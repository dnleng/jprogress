package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.formula.Bottom;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.Top;
import se.liu.ida.jprogress.progressor.graph.Node;

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
    private long[] performance;
    private double quality;

    public ProgressionStatus(List<Node> nodeList, double threshold, long[] performance, double quality) {
        this.massMap = new LinkedHashMap<>();
        this.threshold = threshold;
        if(performance.length == 6) {
            this.performance = performance;
        }
        else {
            this.performance = new long[6];
        }
        for (Node key : nodeList) {
            massMap.put(key.formula, key.mass);
        }
        this.quality = quality;
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
        return 1.0 - totalMass;
    }

    public double getNoVerdict() {
        return 1.0 - (getTrueVerdict() + getFalseVerdict() + getUnknownVerdict());
    }

    public double getZeroBucketSize() {
        int count = 0;
        for (Formula formula : massMap.keySet()) {
            if (massMap.get(formula) == 0.0) {
                count++;
            }
        }

        return (double)count / (double)massMap.keySet().size();
    }

    public double getBucketSize(double lower, double upper) {
        int count = 0;
        for (Formula formula : massMap.keySet()) {
            if(upper < 1.0) {
                if (lower <= massMap.get(formula) && massMap.get(formula) < upper) {
                    count++;
                }
            }
            else {
                if(lower <= massMap.get(formula)) {
                    count++;
                }
            }
        }

        return (double)count / (double)massMap.keySet().size();
    }

    public long[] getPerformance() {
        return this.performance;
    }

    public double getQuality() {
        return this.quality;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State quality: ");
        sb.append(Math.floor(this.quality*1000.0)/1000.0);
        sb.append("\n\n");
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
        if (leakedMass >= threshold) {
            sb.append(Math.floor(leakedMass * 100000.0) / 100000.0);
            sb.append("\t\t:\t?\n");
        }

        sb.append("\n");
        sb.append("Time breakdown (nanoseconds):\n");
        sb.append("Prepare\t:\t");
        sb.append(performance[1] - performance[0]);
        sb.append("\n");
        sb.append("Expand\t:\t");
        sb.append(performance[2] - performance[1]);
        sb.append("\n");
        sb.append("Remove\t:\t");
        sb.append(performance[3] - performance[2]);
        sb.append("\n");
        sb.append("Sort\t:\t");
        sb.append(performance[4] - performance[3]);
        sb.append("\n");
        sb.append("Leak\t:\t");
        sb.append(performance[5] - performance[4]);
        sb.append("\n");
        sb.append("Total\t:\t");
        sb.append(performance[5] - performance[0]);
        sb.append("\n");


        return sb.toString();
    }
}
