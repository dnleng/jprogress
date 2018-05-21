package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.TruthValue;

import java.util.List;

/**
 * Created by dnleng on 06/05/2018.
 */
public class RandomGenerator extends RandomizedGenerator {

    public RandomGenerator(List<String> props) {
        super(props);
    }

    public RandomGenerator(List<String> props, long seed) {
        super(props, seed);
    }

    @Override
    public Interpretation next() {
        Interpretation result = new Interpretation();
        for (String prop : props) {
            switch (this.rnd.nextInt(2)) {
                case 0:
                    result.setTruthValue(prop, TruthValue.TRUE);
                    break;
                case 1:
                    result.setTruthValue(prop, TruthValue.FALSE);
                    break;
                default:
                    result.setTruthValue(prop, TruthValue.UNKNOWN);
                    break;
            }
        }
        return result;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public void reset() {

    }
}
