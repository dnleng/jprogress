package se.liu.ida.jprogress.distribution;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.TruthValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dnleng on 20/06/18.
 */
public class BernoulliDistribution extends DiscreteDistribution {

    public BernoulliDistribution(double prSuccess) {
        super(init(prSuccess));
    }

    private static Map<Integer, Double> init(double prSuccess) {
        Map<Integer, Double> probabilityMap = new HashMap<>();

        Interpretation p = new Interpretation();
        p.setTruthValue("p", TruthValue.TRUE);
        probabilityMap.put(p.compress(), prSuccess);

        Interpretation notP = new Interpretation();
        notP.setTruthValue("p", TruthValue.FALSE);
        probabilityMap.put(notP.compress(), 1.0-prSuccess);

        return probabilityMap;
    }

    @Override
    public IDistribution rebuild(Set<String> props) {
        return this;
    }
}
