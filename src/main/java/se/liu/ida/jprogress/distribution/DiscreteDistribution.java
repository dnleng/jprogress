package se.liu.ida.jprogress.distribution;

import se.liu.ida.jprogress.Interpretation;

import java.util.Map;
import java.util.Set;

/**
 * Created by dnleng on 20/06/18.
 */
public class DiscreteDistribution extends AbstractDistribution {

    public DiscreteDistribution(Map<Integer, Double> probabilityMap) {
        super();
        this.probabilityMap.putAll(probabilityMap);
    }

    @Override
    public IDistribution rebuild(Set<String> props) {
        throw new UnsupportedOperationException("Unable to automatically rebuild distribution");
    }
}
