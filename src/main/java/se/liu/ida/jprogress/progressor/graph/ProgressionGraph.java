package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.TruthValue;

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
public class ProgressionGraph {
    private Map<UUID, Formula> idMap;
    private Map<UUID, Set<Transition>> transitionMap;
    private Map<UUID, Double> massMap;
    private Map<UUID, Boolean> expandedMap;

    public ProgressionGraph() {
	this.idMap = new HashMap<>();
	this.transitionMap = new HashMap<>();
	this.massMap = new HashMap<>();
	this.expandedMap = new HashMap<>();
    }

    public ProgressionGraph(Formula formula) {
	this.idMap = new HashMap<>();
	this.transitionMap = new HashMap<>();
	this.massMap = new HashMap<>();
	this.expandedMap = new HashMap<>();

	precompute(formula);
    }

    public void precompute(Formula formula) {
	List<Formula> frontier = new LinkedList<>();
	frontier.add(formula);

	Map<String, Formula> strMap = new HashMap<>();
	strMap.put(formula.toString(), formula);

	if(formula.getId() == null) {
	    formula.setId(UUID.randomUUID());
	}
	this.idMap.put(formula.getId(), formula);
	this.expandedMap.put(formula.getId(), false);
	this.transitionMap.put(formula.getId(), new HashSet<>());
	this.massMap.put(formula.getId(), 1.0);


	Set<String> atoms = formula.getAtoms();
	Interpretation unknowns = new Interpretation();
	for(String atom : atoms) {
	    unknowns.setTruthValue(atom, TruthValue.UNKNOWN);
	}
	Set<Interpretation> hSet = unknowns.getReductions();
	while(!frontier.isEmpty()) {
	    Formula f = frontier.remove(0);
	    frontier.addAll(expand(f, hSet, strMap));
	}
    }

    private List<Formula> expand(Formula f, Set<Interpretation> hSet, Map<String, Formula> strMap) {
        List<Formula> frontier = new LinkedList<>();

	for(Interpretation i : hSet) {
	    Formula result = f.progress(i);
	    if(strMap.get(result.toString()) == null) {
		// New formula encountered; create a new UUID
		result.setId(UUID.randomUUID());
		frontier.add(result);
		strMap.put(result.toString(), result);
		this.idMap.put(result.getId(), result);
		this.expandedMap.put(result.getId(), false);
		this.massMap.put(result.getId(), 0.0);
		this.transitionMap.put(result.getId(), new HashSet<>());
		this.transitionMap.get(f.getId()).add(new Transition(i, result.getId()));
	    }
	    else {
		// Pre-existing formula encountered; use the pre-existing UUID
		this.transitionMap.get(f.getId()).add(new Transition(i, strMap.get(result.toString()).getId()));
	    }
	}
	this.expandedMap.put(f.getId(), true);
	return frontier;
    }

    public void progress(Interpretation interpretation) {
	Set<Integer> redSet = new HashSet<>();
	for(Interpretation i : interpretation.getReductions()) {
    		redSet.add(i.compress());
	}

	Map<UUID, Double> nextMassMap = new HashMap<>();
	for(UUID id : this.idMap.keySet()) {
	    nextMassMap.put(id, 0.0);
	}

	for(UUID id : this.idMap.keySet()) {
	    if(this.massMap.get(id) > 0.0) {
	        // Push mass
		List<UUID> destinations = new LinkedList<>();
		for(Transition t : this.transitionMap.get(id)) {
		    if(redSet.contains(t.interpretation)) {
			destinations.add(t.destination);
		    }
		}

		double massChunk = this.massMap.get(id) / (double)destinations.size();
		for(UUID destId : destinations) {
		    nextMassMap.put(destId, nextMassMap.get(destId) + massChunk);
		}
	    }
	}

	this.massMap = nextMassMap;
    }

    public String getMassStatus() {
	StringBuilder sb = new StringBuilder();
	sb.append("Probability mass distribution:\n");
	for(UUID key : this.idMap.keySet()) {
	    Formula formula = this.idMap.get(key);
	    double mass = Math.floor(this.massMap.get(key) * 10000.0) / 10000.0;

	    if(mass > 0.001) {
		sb.append(mass);
		sb.append("\t\t:\t");
		sb.append(formula);
		sb.append("\n");
	    }
	}

	return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

	sb.append("Vertex count\t: ");
	sb.append(this.idMap.keySet().size());
	sb.append("\n");
	sb.append("Transitions:\n");
	for(UUID id : transitionMap.keySet()) {
	    sb.append(idMap.get(id));
	    sb.append("\n");
	    for(Transition transition : transitionMap.get(id)) {
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
