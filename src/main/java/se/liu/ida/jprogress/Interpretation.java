package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.TruthValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Interpretation {

    private Map<String, TruthValue> truthFunc;

    public Interpretation() {
        this.truthFunc = new HashMap<String, TruthValue>();
    }

    public TruthValue getTruthValue(String label) {
        return this.truthFunc.getOrDefault(label, TruthValue.UNKNOWN);
    }

    public void setTruthValue(String label, TruthValue value) {
        this.truthFunc.put(label, value);
    }

    public Set<Interpretation> reduce() {
        return null;
    }
}
