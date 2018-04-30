package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by dnleng on 30/04/18.
 */
public class Negation extends Formula {
    private Formula formula;

    public Negation(Formula formula) {
        this.formula = formula;
    }

    public Formula progress(Interpretation interpretation) {
        return new Negation(this.formula.progress(interpretation));
    }

    public TruthValue eval(Interpretation interpretation) {
        if(this.formula.eval(interpretation) == TruthValue.UNKNOWN) {
            return TruthValue.UNKNOWN;
        }
        else if(this.formula.eval(interpretation) == TruthValue.TRUE) {
            return TruthValue.FALSE;
        }
        else {
            return TruthValue.TRUE;
        }
    }

    @Override
    public String toString() {
        return "¬(" + this.formula + ")";
    }
}
