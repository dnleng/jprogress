package se.liu.ida.jprogress.distribution;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.reasoning.InconsistentStateException;

import java.util.Set;

/**
 * Created by dnleng on 20/06/18.
 */
public class UniformDistribution extends AbstractDistribution {

    public UniformDistribution(Set<String> props) {
        super();
        rebuild(props);
    }

    @Override
    public IDistribution rebuild(Set<String> props) {
        this.probabilityMap.clear();
        Interpretation interpretation = Interpretation.buildFullyUnknown(props);
        try {
            for(Interpretation i : interpretation.getReductions()) {
                this.setProbability(i.compress(), 1.0 / (double)(Math.pow(2, props.size())));
            }
        } catch (InconsistentStateException e) {
            System.err.println(e.getMessage());
            this.probabilityMap.clear();
        }

        return this;
    }
}
