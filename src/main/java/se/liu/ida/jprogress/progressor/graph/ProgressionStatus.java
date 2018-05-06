package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.formula.Bottom;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.Top;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Squig on 06/05/2018.
 */
public class ProgressionStatus {

    private Map<Formula, Double> massMap;
    private double threshold;

    public ProgressionStatus(List<Node> nodeList, double threshold) {
        this.massMap = new LinkedHashMap<>();
        this.threshold = threshold;
	for (Node key : nodeList) {
	    massMap.put(key.formula, key.mass);
	}
    }

    public Map<Formula, Double> getMassMap() {
        return this.massMap;
    }

    public double getTrueVerdict() {
        return massMap.getOrDefault(Top.getInstance(), 0.0);
    }

    public double getFalseVerdict() {
	return massMap.getOrDefault(Bottom.getInstance(), 0.0);
    }

    public double getUnknownVerdict() {
        double totalMass = 0.0;
	for (Formula formula : massMap.keySet()) {
	    totalMass += massMap.get(formula);
	}
	return 1.0-totalMass;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Probability mass distribution:\n");
	double totalMass = 0;
	for (Formula formula : massMap.keySet()) {
	    double mass = Math.floor(massMap.get(formula) * 100000.0) / 100000.0;
	    totalMass += massMap.get(formula);

	    if (mass >= threshold) {
		sb.append(mass);
		sb.append("\t\t:\t");
		sb.append(formula);
		sb.append("\n");
	    }
	}

	double leakedMass = 1.0 - totalMass;
	if(leakedMass >= threshold) {
	    sb.append(Math.floor(leakedMass * 100000.0) / 100000.0);
	    sb.append("\t\t:\t?\n");
	}

	return sb.toString();
    }
}
