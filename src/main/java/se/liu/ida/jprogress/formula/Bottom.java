package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

/**
 * Created by dnleng on 30/04/18.
 */
public class Bottom extends Atom {

    public Bottom() {
        super("⊥");
    }

    @Override
    public Formula progress(Interpretation interpretation) {
        return this;
    }

    @Override
    public TruthValue eval(Interpretation interpretation) {
        return TruthValue.FALSE;
    }
}
