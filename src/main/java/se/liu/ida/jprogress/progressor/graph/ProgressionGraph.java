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
    private static final int MAX_ITER = Integer.MAX_VALUE; //FIXME: Add option to set this externally for debugging
    private Map<UUID, Node> map;



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
        this.map = new HashMap<>();
        this.map.put(formula.getId(), root);
    }

    private void precompute(Formula formula, int timeout) {
        List<Formula> frontier = new LinkedList<>();
        frontier.add(formula);

        if (formula.getId() == null) {
            formula.setId(UUID.randomUUID());
        }

        Set<String> atoms = formula.getAtoms();
        Interpretation unknowns = new Interpretation();
        for (String atom : atoms) {
            unknowns.setTruthValue(atom, TruthValue.UNKNOWN);
        }
        Set<Interpretation> hSet = unknowns.getReductions();
        int iter = 0;
        while (!frontier.isEmpty()) {
            Formula f = frontier.remove(0);
            frontier.addAll(expand(f, hSet));
            if(timeout < Integer.MAX_VALUE) {
                iter++;
                if(iter > timeout) {
                    break;
                }
            }
        }
    }

    private List<Formula> expand(Formula f, Set<Interpretation> hSet) {
        List<Formula> frontier = new LinkedList<>();

        for (Interpretation i : hSet) {
            Node src = this.map.get(f.getId());
            Formula result = f.progressOnce(i);
            UUID id = getUUID(result);
            if (id == null) {
                // New formula encountered; create a new UUID
                result.setId(UUID.randomUUID());
                frontier.add(result);

                Node dest = new Node(result, new HashSet<>(), 0.0, false, 0);
                this.map.put(result.getId(), dest);
                src.transitions.add(new Transition(i, result.getId()));
            } else {
                // Pre-existing formula encountered; use the pre-existing UUID
                src.transitions.add(new Transition(i, id));
            }
        }

        this.map.get(f.getId()).expanded = true;

        return frontier;
    }

    private void shrink(Set<UUID> destIds) {
        Set<UUID> resetSet = new HashSet<>();
        for(UUID srcId : this.map.keySet()) {
            for(Transition trans : this.map.get(srcId).transitions) {
                if(destIds.contains(trans.destination)) {
                    resetSet.add(srcId);
                }
            }
        }

        for(UUID id : resetSet) {
            this.map.get(id).expanded = false;
            this.map.get(id).transitions = new HashSet<>();
        }

        for(UUID id : destIds) {
            this.map.remove(id);
        }
    }

    private UUID getUUID(String strFormula) {
        for(UUID id : this.map.keySet()) {
            if(this.map.get(id).toString().equals(strFormula)) {
                return id;
            }
        }

        return null;
    }

    private UUID getUUID(Formula formula) {
        if(formula.getId() != null) {
            return formula.getId();
        }
        else {
            for (UUID id : this.map.keySet()) {
                if (this.map.get(id).formula.equals(formula)) {
                    return id;
                }
            }
        }

        return null;
    }

    @Override
    public void progress(Interpretation interpretation) {
        Set<Integer> redSet = new HashSet<>();
        for (Interpretation i : interpretation.getReductions()) {
            redSet.add(i.compress());
        }

        Map<UUID, Double> nextMassMap = new HashMap<>();
        for (UUID id : this.map.keySet()) {
            nextMassMap.put(id, 0.0);
        }

        // Specify job list (since idMap gets updated) and execute
        List<UUID> jobList = new ArrayList<>(this.map.keySet().size());
        jobList.addAll(this.map.keySet());
        for (UUID id : jobList) {
            if (this.map.get(id).mass > 0.0) {
                // Update graph if necessary
                if(!this.map.get(id).expanded) {
                    expand(this.map.get(id).formula, Interpretation.buildFullyUnknown(interpretation.getAtoms()).getReductions());
                }

                // Push mass
                List<UUID> destinations = new LinkedList<>();
                for (Transition t : this.map.get(id).transitions) {
                    if (redSet.contains(t.interpretation)) {
                        destinations.add(t.destination);
                    }
                }

                double massChunk = this.map.get(id).mass / (double) destinations.size();
                for (UUID destId : destinations) {
                    nextMassMap.put(destId, nextMassMap.getOrDefault(destId, 0.0) + massChunk);
                    this.map.get(destId).age = 0;
                }
            }
        }

        for(UUID id : nextMassMap.keySet()) {
            this.map.get(id).mass = nextMassMap.get(id);
        }

        if(maxTTL < Integer.MAX_VALUE) {
            Set<UUID> removalSet = new HashSet<>();
            for(UUID id : this.map.keySet()) {
                int newAge = this.map.get(id).age + 1;
                if(newAge < maxTTL) {
                    this.map.get(id).age = newAge;
                }
                else {
                    removalSet.add(id);
                }
            }
            shrink(removalSet);
        }
    }

    @Override
    public void set(Formula input) {
        reset();
        switch(this.strategy) {
            case GRAPH:
                init(input);
                precompute(input, MAX_ITER);
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
        this.map = new HashMap<>();
        this.strategy = strategy;
        this.maxTTL = Integer.MAX_VALUE;
    }

    public void setTTL(int ttl) {
        this.maxTTL = ttl;
    }

    public String getMassStatus(double threshold) {
        StringBuilder sb = new StringBuilder();
        sb.append("Probability mass distribution:\n");
        double totalMass = 0;
        for (UUID key : sortByValue(this.map).keySet()) {
            Formula formula = this.map.get(key).formula;
            double mass = Math.floor(this.map.get(key).mass * 100000.0) / 100000.0;
            totalMass += this.map.get(key).mass;

            if (mass >= threshold) {
                sb.append(mass);
                sb.append("\t\t:\t");
                sb.append(formula);
                sb.append("\t\t{TTL: ");
                sb.append(this.maxTTL-this.map.get(key).age);
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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        // Source: https://stackoverflow.com/a/2581754/9623204 (modified to reverse)
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public String getGraphStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph properties:\n");
        sb.append("Vertex count\t\t:\t");
        sb.append(this.map.keySet().size());
        sb.append("\n");

        int edgeCount = 0;
        for(UUID id : this.map.keySet()) {
            edgeCount += this.map.get(id).transitions.size();
        }

        sb.append("Edge count  \t\t:\t");
        sb.append(edgeCount);
        sb.append("\n");

        sb.append("Time-to-live\t\t:\t");
        sb.append(this.maxTTL);
        sb.append("\n");

        return sb.toString();
    }

    public String getMassStatus() {
        return getMassStatus(0.0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vertex count\t: ");
        sb.append(this.map.keySet().size());
        sb.append("\n");
        sb.append("Transitions:\n");
        for (UUID id : this.map.keySet()) {
            sb.append(this.map.get(id).formula);
            sb.append("\n");
            for (Transition transition : this.map.get(id).transitions) {
                sb.append("\t { ");
                sb.append(transition.interpretation);
                sb.append(" } ");
                sb.append(this.map.get(transition.destination).formula.toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
