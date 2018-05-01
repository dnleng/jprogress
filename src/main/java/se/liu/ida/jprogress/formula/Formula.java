package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by dnleng on 30/04/18.
 */
public abstract class Formula {
    public abstract Formula progress(Interpretation interpretation);
    public abstract TruthValue eval(Interpretation interpretation);

    public Formula simplify(Interpretation interpretation) {
        if(this.eval(interpretation) == TruthValue.TRUE) {
            return new Top();
        }
        else if(this.eval(interpretation) == TruthValue.FALSE) {
            return new Bottom();
        }
        else {
            return this;
        }
    }
}
