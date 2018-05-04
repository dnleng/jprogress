package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.progressor.Progressor;

import java.util.*;

/**
 * Created by Squig on 01/05/2018.
 */
public class ProgressionGraph implements Progressor {
    private ProgressionStrategy strategy;
    private int maxTTL;
    private int maxNodes;
    private List<Node> nodeList;



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
        while (!frontier.isEmpty()) {
            Node f = frontier.remove(0);
            frontier.addAll(expand(f, hSet));
        }
    }

    private List<Node> expand(Node src, Set<Interpretation> hSet) {
        List<Node> frontier = new LinkedList<>();

        for (Interpretation i : hSet) {
            Formula result = src.formula.progressOnce(i);

            Node dest = null;
            for(Node n : nodeList) {
                if(n.formula.equals(result)) {
                    dest = n;
                    break;
                }
            }

            if (dest == null) {
                dest = new Node(result, new HashSet<>(), 0.0, false, 0);
                this.nodeList.add(dest);
                src.transitions.add(new Transition(i, null, dest));
                frontier.add(dest);
            } else {
                // Pre-existing formula encountered; use the pre-existing UUID
                src.transitions.add(new Transition(i, null, dest));
            }
        }

        src.expanded = true;
        return frontier;
    }

    private void shrink(Set<Node> destIds) {
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
        Set<Integer> redSet = new HashSet<>();
        for (Interpretation i : interpretation.getReductions()) {
            redSet.add(i.compress());
        }

        Map<Node, Double> nextMassMap = new HashMap<>();
        for (Node n : this.nodeList) {
            nextMassMap.put(n, 0.0);
        }

        // Specify job list and execute
        List<Node> jobList = new LinkedList<>();
        jobList.addAll(this.nodeList);

        for (Node id : jobList) {
            if (id.mass > 0.0) {
                // Update graph if necessary
                if(!id.expanded) {
                    expand(id, Interpretation.buildFullyUnknown(interpretation.getAtoms()).getReductions());
                }

                // Push mass
                List<Node> destinations = new LinkedList<>();
                for (Transition t : id.transitions) {
                    if (redSet.contains(t.interpretation)) {
                        destinations.add(t.destNode);
                    }
                }

                double massChunk = id.mass / (double) destinations.size();
                for (Node dest : destinations) {
                    nextMassMap.put(dest, nextMassMap.getOrDefault(dest, 0.0) + massChunk);
                    dest.age = 0;
                }
            }
            else {
                // List is sorted by mass, so we can break
                break;
            }
        }

        // Check whether we need to destroy some of these old nodes
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


        // Leak mass where needed
        while(this.nodeList.size() - maxNodes > 0) {
            Node removed = this.nodeList.remove(this.nodeList.size()-1);
//            System.out.println("Leaking " + removed.mass + " mass");
        }

        // Sort by mass
        Collections.sort(this.nodeList);
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
        this.maxTTL = ttl;
    }

    public void setMaxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }


    public String getMassStatus(double threshold) {
        StringBuilder sb = new StringBuilder();
        sb.append("Probability mass distribution:\n");
        double totalMass = 0;
        for (Node key : this.nodeList) {
            Formula formula = key.formula;
            double mass = Math.floor(key.mass * 100000.0) / 100000.0;
            totalMass += key.mass;

            if (mass >= threshold) {
                sb.append(mass);
                sb.append("\t\t:\t");
                sb.append(formula);
                sb.append("\t\t{TTL: ");
                sb.append(this.maxTTL-key.age);
                sb.append("}\n");
            }
        }

        double leakedMass = 1.0 - totalMass;
        if(leakedMass >= threshold) {
            sb.append(Math.floor(leakedMass * 100000.0) / 100000.0);
            sb.append("\t\t:\t?\n");
        }

        return sb.toString();
    }

    public String getGraphStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph properties:\n");

        sb.append("Graph strategy\t\t:\t");
        sb.append(this.strategy);
        sb.append("\n");

        sb.append("Component count\t\t:\t");
        sb.append(Formula.getCount());
        sb.append("\n");

        sb.append("Vertex count\t\t:\t");
        sb.append(this.nodeList.size());
        sb.append("\n");

        int edgeCount = 0;
        for(Node id : this.nodeList) {
            edgeCount += id.transitions.size();
        }

        sb.append("Edge count  \t\t:\t");
        sb.append(edgeCount);
        sb.append("\n");

        sb.append("Time-to-live\t\t:\t");
        sb.append(this.maxTTL);
        sb.append("\n");

        sb.append("Max node bound\t\t:\t");
        sb.append(this.maxNodes);
        sb.append("\n");

        return sb.toString();
    }

    public String getMassStatus() {
        return getMassStatus(0.0);
    }

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
