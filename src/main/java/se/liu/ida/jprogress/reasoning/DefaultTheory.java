package se.liu.ida.jprogress.reasoning;

import se.liu.ida.jprogress.formula.TruthValue;

import java.util.Map;

/**
 * Created by Squig on 17/05/2018.
 */
public class DefaultTheory implements IClosure {

    public DefaultTheory() {}

    @Override
    public boolean close(Map<String, TruthValue> truthFunc) {
        return true;
    }
}
