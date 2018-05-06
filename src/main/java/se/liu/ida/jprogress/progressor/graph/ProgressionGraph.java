package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.Main;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.progressor.ProgressionStatus;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.progressor.Progressor;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Squig on 01/05/2018.
 */
public class ProgressionGraph implements Progressor {
    private ProgressionStrategy strategy;
    private int maxTTL;
    private int maxNodes;
    private List<Node> nodeList;
    private final Object nodeListLock = new Object();
    private ExecutorService executorService;

    public ProgressionGraph(ProgressionStrategy strategy) {
        reset(strategy);
    }

    public ProgressionGraph(ProgressionStrategy strategy, Formula formula) {
        reset(strategy);
        set(formula);
    }

    private void init(Formula formula) {
        // Build the starting node
        if (formula.getId() == null) {
            formula.setId(UUID.randomUUID());
        }

        Node root = new Node(formula, new HashSet<>(), 1.0, false, 0);
        this.nodeList = new LinkedList<>();
        this.nodeList.add(root);
    }

    private void precompute(Formula formula) {
        List<Node> frontier = new LinkedList<>();
        frontier.addAll(this.nodeList);

        if (formula.getId() == null) {
            formula.setId(UUID.randomUUID());
        }

        Set<String> atoms = formula.getAtoms();
        Interpretation unknowns = new Interpretation();
        for (String atom : atoms) {
            unknowns.setTruthValue(atom, TruthValue.UNKNOWN);
        }
        Set<Interpretation> hSet = unknowns.getReductions();
        while (!frontier.isEmpty() && this.nodeList.size() < this.maxNodes) {
            Node f = frontier.remove(0);
            frontier.addAll(expand(f, hSet));
        }
    }

    public List<Node> expand(Node src, Set<Interpretation> hSet) {
        List<Node> frontier = new LinkedList<>();

        for (Interpretation i : hSet) {
            Formula result = src.formula.progressOnce(i);

            synchronized(nodeListLock) {
                Node dest = null;
                for (Node n : nodeList) {
                    if (n.formula.equals(result)) {
                        dest = n;
                        break;
                    }
                }

                if (dest == null) {
                    dest = new Node(result, new HashSet<>(), 0.0, false, 0);
                    this.nodeList.add(dest);
                    src.transitions.add(new Transition(i, dest));
                    frontier.add(dest);
                } else {
                    // Pre-existing formula encountered; use the pre-existing UUID
                    src.transitions.add(new Transition(i, dest));
                }
            }
        }

        src.expanded = true;
        return frontier;
    }

    public void shrink(Set<Node> destIds) {
        Set<Node> resetSet = new HashSet<>();
        for(Node srcId : this.nodeList) {
            for(Transition trans : srcId.transitions) {
                if(destIds.contains(trans.destNode)) {
                    resetSet.add(srcId);
                }
            }
        }

        for(Node id : resetSet) {
            id.expanded = false;
            id.transitions = new HashSet<>();
        }

        this.nodeList.removeAll(destIds);
    }

    @Override
    public void progress(Interpretation interpretation) {
        long tStart = System.currentTimeMillis();

        List<Integer> redSet = new LinkedList<>();
        for (Interpretation i : interpretation.getReductions()) {
            redSet.add(i.compress());
        }

        final Object massMapLock = new Object();
        Map<Node, Double> nextMassMap = new HashMap<>();
        for (Node n : this.nodeList) {
            nextMassMap.put(n, 0.0);
        }

        // Specify job list and execute
        List<Node> jobList = new LinkedList<>();
        jobList.addAll(this.nodeList);

        // Handle expansion (async)
        long tExpand = System.currentTimeMillis();
        executorService = Executors.newFixedThreadPool(Main.MAX_THREADS);
        List<Callable<Object>> callList = new LinkedList<>();
        for (Node id : jobList) {
            if (id.mass > 0.0) {
                callList.add(Executors.callable(() -> {
		    if(!id.expanded) {
			expand(id, Interpretation.buildFullyUnknown(interpretation.getAtoms()).getReductions());
		    }

		    List<Node> destinations = new LinkedList<>();
		    for (Transition t : id.transitions) {
			if (redSet.contains(t.interpretation)) {
			    destinations.add(t.destNode);
			}
		    }

		    double massChunk = id.mass / (double) destinations.size();
		    for (Node dest : destinations) {
			synchronized(massMapLock) {
			    nextMassMap.put(dest, nextMassMap.getOrDefault(dest, 0.0) + massChunk);
			}
			dest.age = 0;
		    }
		}));
            }
            else {
                // List is sorted by mass, so we can break
                break;
            }
        }

        try {
            executorService.invokeAll(callList);
            executorService.shutdown();
           while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
               System.out.println("Awaiting termination");
           }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        // Handle expansion (sync)
//        for (Node id : jobList) {
//            if (id.mass > 0.0) {
//
//                if(!id.expanded) {
//                    expand(id, Interpretation.buildFullyUnknown(interpretation.getAtoms()).getReductions());
//                }
//
//                List<Node> destinations = new LinkedList<>();
//                for (Transition t : id.transitions) {
//                    if (redSet.contains(t.interpretation)) {
//                        destinations.add(t.destNode);
//                    }
//                }
//
//                double massChunk = id.mass / (double) destinations.size();
//                for (Node dest : destinations) {
//                    nextMassMap.put(dest, nextMassMap.getOrDefault(dest, 0.0) + massChunk);
//                    dest.age = 0;
//                }
//            }
//            else {
//                // List is sorted by mass, so we can break
//                break;
//            }
//        }

        // Check whether we need to destroy some of these old nodes
        long tRemove = System.currentTimeMillis();
        Set<Node> removalSet = new HashSet<>();
        for(Node id : nextMassMap.keySet()) {
            // Update nodes
            id.mass = nextMassMap.get(id);
            id.age++;

            if(id.age > maxTTL) {
                removalSet.add(id);
            }
        }
        shrink(removalSet);

        // Sort by mass
        long tSort = System.currentTimeMillis();
        Collections.sort(this.nodeList);

        // Leak mass where needed
        long tLeak = System.currentTimeMillis();
        while(this.nodeList.size() - maxNodes > 0) {
            this.nodeList.remove(this.nodeList.size()-1);
        }

        long tEnd = System.currentTimeMillis();

//        StringBuilder sb = new StringBuilder();
//        sb.append("Time breakdown:\n");
//
//        sb.append("Prepare\t:\t");
//        sb.append(tExpand - tStart);
//        sb.append("ms\n");
//
//        sb.append("Expand\t:\t");
//        sb.append(tRemove - tExpand);
//        sb.append("ms\n");
//
//        sb.append("Remove\t:\t");
//        sb.append(tSort - tRemove);
//        sb.append("ms\n");
//
//        sb.append("Sort\t:\t");
//        sb.append(tLeak - tSort);
//        sb.append("ms\n");
//
//        sb.append("Leak\t:\t");
//        sb.append(tEnd - tLeak);
//        sb.append("ms\n");
//
//        sb.append("Total\t:\t");
//        sb.append(tEnd - tStart);
//        sb.append("ms\n");
//
//        System.out.println(sb.toString());
    }

    @Override
    public void set(Formula input) {
        reset();
        switch(this.strategy) {
            case OFFLINE:
                init(input);
                precompute(input);
                break;
            default:
                init(input);
                break;
        }
    }

    public void reset() {
        reset(this.strategy);
    }

    public void reset(ProgressionStrategy strategy) {
        this.nodeList = new LinkedList<>();
        this.strategy = strategy;
        this.maxTTL = Integer.MAX_VALUE;
        this.maxNodes = Integer.MAX_VALUE;
    }

    public void setTTL(int ttl) {
        if(ttl > 0) {
            this.maxTTL = ttl;
        }
    }

    public void setMaxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }


    public ProgressionStatus getMassStatus(double threshold) {
        ProgressionStatus status = new ProgressionStatus(this.nodeList, threshold);
        return status;
    }

    public GraphStatus getGraphStatus() {
        int edgeCount = 0;
        for(Node id : this.nodeList) {
            edgeCount += id.transitions.size();
        }

        return new GraphStatus(this.strategy, edgeCount, this.nodeList.size(), this.maxTTL, this.maxNodes);
    }

    public ProgressionStatus getMassStatus() {
        return getMassStatus(0.0);
    }

    @Override
    public ProgressionStatus getStatus() {
        return getMassStatus();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vertex count\t: ");
        sb.append(this.nodeList.size());
        sb.append("\n");
        sb.append("Transitions:\n");
        for (Node id : this.nodeList) {
            sb.append(id.formula);
            sb.append("\n");
            for (Transition transition : id.transitions) {
                sb.append("\t { ");
                sb.append(transition.interpretation);
                sb.append(" } ");
                sb.append(transition.destNode.formula.toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
