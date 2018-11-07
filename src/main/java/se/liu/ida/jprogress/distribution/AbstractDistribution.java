package se.liu.ida.jprogress.distribution;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dnleng on 20/06/18.
 */
public abstract class AbstractDistribution implements IDistribution {

    protected Map<Integer, Double> probabilityMap;

    public AbstractDistribution() {
        this.probabilityMap = new HashMap<>();
    }

    @Override
    public double getProbability(int interpretation) {
        return probabilityMap.getOrDefault(interpretation, 0.0);
    }

    public void setProbability(int interpretation, double probability) {
        this.probabilityMap.put(interpretation, probability);
    }

    @Override
    public abstract IDistribution rebuild(Set<String> atoms);

    public boolean verify() {
        double sum = 0.0;
        for(int i : probabilityMap.keySet()) {
            sum += probabilityMap.get(i);
        }

        return sum == 1.0;
    }
}
