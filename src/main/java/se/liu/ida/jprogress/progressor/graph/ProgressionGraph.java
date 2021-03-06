package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.Main;
import se.liu.ida.jprogress.distribution.IDistribution;
import se.liu.ida.jprogress.distribution.UniformDistribution;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.progressor.ProgressionStatus;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.progressor.Progressor;
import se.liu.ida.jprogress.progressor.ProgressorProperties;
import se.liu.ida.jprogress.reasoning.InconsistentStateException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by dnleng on 01/05/2018.
 */
public class ProgressionGraph implements Progressor {
    private ProgressionStrategy strategy;
    private IDistribution distribution;
    private int maxTTL;
    private int maxNodes;
    private List<Node> nodeList;
    private final Object nodeListLock = new Object();
    private ExecutorService executorService;
    private long[] prevPerformance;
    private double prevQuality;

    public ProgressionGraph(ProgressionStrategy strategy, IDistribution distribution) {
        reset(strategy, distribution);
    }

    public ProgressionGraph(ProgressionStrategy strategy, IDistribution distribution, Formula formula) {
        reset(strategy, distribution);
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
        long start = System.nanoTime();;
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
        Set<Interpretation> hSet = null;
        try {
            hSet = unknowns.getReductions();
        } catch (InconsistentStateException e) {
            e.printStackTrace();
        }
        while (!frontier.isEmpty() && this.nodeList.size() < this.maxNodes) {
            Node f = frontier.remove(0);
            frontier.addAll(expand(f, hSet));
        }
        long end = System.nanoTime();
        System.out.println("Precomputation time: " + (end-start));
    }

    public List<Node> expand(Node src, Set<Interpretation> hSet) {
        List<Node> frontier = new LinkedList<>();

        for (Interpretation i : hSet) {
            Formula result = src.formula.progressOnce(i);

            synchronized (nodeListLock) {
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
        for (Node srcId : this.nodeList) {
            for (Transition trans : srcId.transitions) {
                if (destIds.contains(trans.destNode)) {
                    resetSet.add(srcId);
                }
            }
        }

        for (Node id : resetSet) {
            id.expanded = false;
            id.transitions = new HashSet<>();
        }

        this.nodeList.removeAll(destIds);
    }

    @Override
    public void progress(Interpretation interpretation) {
        prevPerformance = new long[6];
        prevPerformance[0] = System.nanoTime();

        List<Integer> redSet = new LinkedList<>();
        try {
            for (Interpretation i : interpretation.getReductions()) {
                redSet.add(i.compress());
            }
        } catch (InconsistentStateException e) {
            e.printStackTrace();
        }
        prevQuality = 1.0 - (((double)redSet.size()-1.0) / (Math.pow(2, interpretation.getAtoms().size())-1.0));

        final Object massMapLock = new Object();
        Map<Node, Double> nextMassMap = new HashMap<>();
        for (Node n : this.nodeList) {
            nextMassMap.put(n, 0.0);
        }

        // Specify job list and execute
        List<Node> jobList = new LinkedList<>();
        jobList.addAll(this.nodeList);

        // Handle expansion (async)
        prevPerformance[1] = System.nanoTime();
        executorService = Executors.newFixedThreadPool(Main.MAX_THREADS);
        List<Callable<Object>> callList = new LinkedList<>();
        for (Node id : jobList) {
            if (id.mass > 0.0) {
                callList.add(Executors.callable(() -> {
                    if (!id.expanded) {
                        try {
                            expand(id, Interpretation.buildFullyUnknown(interpretation.getAtoms()).getReductions());
                        } catch (InconsistentStateException e) {
                            e.printStackTrace();
                        }
                    }

                    double massDivider = 0.0;
                    List<Node> destinations = new LinkedList<>();
                    Map<Node, Double> stateProb = new HashMap<>();
                    for (Transition t : id.transitions) {
                        if (redSet.contains(t.interpretation)) {
                            if(!destinations.contains(t.destNode)) {
                                destinations.add(t.destNode);
                            }
                            stateProb.put( t.destNode, stateProb.getOrDefault(t.destNode, 0.0) + this.distribution.getProbability(t.interpretation));
                            massDivider += this.distribution.getProbability(t.interpretation);
                        }
                    }

                    for (Node dest : destinations) {
                        synchronized (massMapLock) {
                            nextMassMap.put(dest, nextMassMap.getOrDefault(dest, 0.0) + id.mass * (stateProb.get(dest) / massDivider));
//                            System.out.println(id.mass + "*" + "(" + stateProb.get(dest) + "/" + massDivider + ")");
//                            nextMassMap.put(dest, nextMassMap.getOrDefault(dest, 0.0) + id.mass * (1.0 / destinations.size()));
                        }
                        dest.age = 0;
                    }
                }));
            } else {
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

        // Check whether we need to destroy some of these old nodes
        prevPerformance[2] = System.nanoTime();
        Set<Node> removalSet = new HashSet<>();
        for (Node id : nextMassMap.keySet()) {
            // Update nodes
            id.mass = nextMassMap.get(id);
            id.age++;

            if (id.age > maxTTL) {
                removalSet.add(id);
            }
        }
        shrink(removalSet);

        // Sort by mass
        prevPerformance[3] = System.nanoTime();
        Collections.sort(this.nodeList);

        // Leak mass where needed
        prevPerformance[4] = System.nanoTime();
        removalSet = new HashSet<>();
        while (this.nodeList.size() - maxNodes > 0) {
            removalSet.add(this.nodeList.remove(this.nodeList.size() - 1));
        }
        shrink(removalSet);

        prevPerformance[5] = System.nanoTime();
    }

    @Override
    public void set(Formula input) {
        reset(input);
        switch (this.strategy) {
            case OFFLINE:
                init(input);
                precompute(input);
                break;
            default:
                init(input);
                break;
        }
    }

    public void reset(Formula input) {
        reset(this.strategy, this.distribution.rebuild(input.getAtoms()));
    }

    public void reset(ProgressionStrategy strategy, IDistribution distribution) {
        this.nodeList = new LinkedList<>();
        this.strategy = strategy;
        this.distribution = distribution;
        this.maxTTL = Integer.MAX_VALUE;
        this.maxNodes = Integer.MAX_VALUE;
    }

    public void setTTL(int ttl) {
        if (ttl > 0) {
            this.maxTTL = ttl;
        }
    }

    public void setMaxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }


    public ProgressionStatus getMassStatus(double threshold) {
        ProgressionStatus status = new ProgressionStatus(this.nodeList, threshold, this.prevPerformance, this.prevQuality);
        return status;
    }

    @Override
    public ProgressorProperties getProperties() {
        int edgeCount = 0;
        int componentCount = 0;
        for (Node id : this.nodeList) {
            edgeCount += id.transitions.size();
            componentCount += id.formula.getSize();
        }

        return new ProgressorProperties(this.strategy, edgeCount, this.nodeList.size(), componentCount, this.maxTTL, this.maxNodes);
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
