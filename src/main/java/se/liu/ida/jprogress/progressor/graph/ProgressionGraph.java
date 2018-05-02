package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.progressor.Progressor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Squig on 01/05/2018.
 */
public class ProgressionGraph implements Progressor {
    private ProgressionStrategy strategy;
    private Map<UUID, Formula> idMap;
    private Map<UUID, Set<Transition>> transitionMap;
    private Map<UUID, Double> massMap;
    private Map<UUID, Boolean> expandedMap;
    private Formula root;

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

        this.idMap.put(formula.getId(), formula);
        this.expandedMap.put(formula.getId(), false);
        this.transitionMap.put(formula.getId(), new HashSet<>());
        this.massMap.put(formula.getId(), 1.0);

        this.root = formula;
    }

    private void precompute(Formula formula) {
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
        while (!frontier.isEmpty()) {
            Formula f = frontier.remove(0);
            frontier.addAll(expand(f, hSet));
        }
    }

    private List<Formula> expand(Formula f, Set<Interpretation> hSet) {
        List<Formula> frontier = new LinkedList<>();

        for (Interpretation i : hSet) {
            Formula result = f.progress(i);
            UUID id = getUUID(result.toString());
            if (id == null) {
                // New formula encountered; create a new UUID
                result.setId(UUID.randomUUID());
                frontier.add(result);
                this.idMap.put(result.getId(), result);
                this.expandedMap.put(result.getId(), false);
                this.massMap.put(result.getId(), 0.0);
                this.transitionMap.put(result.getId(), new HashSet<>());
                this.transitionMap.get(f.getId()).add(new Transition(i, result.getId()));
            } else {
                // Pre-existing formula encountered; use the pre-existing UUID
                this.transitionMap.get(f.getId()).add(new Transition(i, id));
            }
        }
        this.expandedMap.put(f.getId(), true);
        return frontier;
    }

    private UUID getUUID(String strFormula) {
        for(UUID id : this.idMap.keySet()) {
            if(this.idMap.get(id).toString().equals(strFormula)) {
                return id;
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
        for (UUID id : this.idMap.keySet()) {
            nextMassMap.put(id, 0.0);
        }

        for (UUID id : this.idMap.keySet()) {
            if (this.massMap.get(id) > 0.0) {
                // Push mass
                List<UUID> destinations = new LinkedList<>();
                for (Transition t : this.transitionMap.get(id)) {
                    if (redSet.contains(t.interpretation)) {
                        destinations.add(t.destination);
                    }
                }

                double massChunk = this.massMap.get(id) / (double) destinations.size();
                for (UUID destId : destinations) {
                    nextMassMap.put(destId, nextMassMap.get(destId) + massChunk);
                }
            }
        }

        this.massMap = nextMassMap;
    }

    @Override
    public void set(Formula input) {
        reset();
        switch(this.strategy) {
            case GRAPH:
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
        this.idMap = new HashMap<>();
        this.transitionMap = new HashMap<>();
        this.massMap = new HashMap<>();
        this.expandedMap = new HashMap<>();
        this.strategy = strategy;
        this.root = null;
    }

    public String getMassStatus(double threshold) {
        StringBuilder sb = new StringBuilder();
        sb.append("Probability mass distribution:\n");
        double totalMass = 0;
        for (UUID key : this.idMap.keySet()) {
            Formula formula = this.idMap.get(key);
            double mass = Math.floor(this.massMap.get(key) * 10000.0) / 10000.0;
            totalMass += this.massMap.get(key);

            if (mass > threshold) {
                sb.append(mass);
                sb.append("\t\t:\t");
                sb.append(formula);
                sb.append("\n");
            }
        }

        double leakedMass = 1.0 - totalMass;
        if(leakedMass > threshold) {
            sb.append(leakedMass);
            sb.append("\t\t:\t?\n");
        }

        return sb.toString();
    }

    public String getMassStatus() {
        return getMassStatus(0.0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vertex count\t: ");
        sb.append(this.idMap.keySet().size());
        sb.append("\n");
        sb.append("Transitions:\n");
        for (UUID id : transitionMap.keySet()) {
            sb.append(idMap.get(id));
            sb.append("\n");
            for (Transition transition : transitionMap.get(id)) {
                sb.append("\t { ");
                sb.append(transition.interpretation);
                sb.append(" } ");
                sb.append(idMap.get(transition.destination).toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
