package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by dnleng on 30/04/18.
 */
public class Disjunction extends Formula {

    private Formula lhs;
    private Formula rhs;

    public Disjunction(Formula lhs, Formula rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Formula progress(Interpretation interpretation) {
        return new Disjunction(lhs.progress(interpretation), rhs.progress(interpretation));
    }

    public TruthValue eval(Interpretation interpretation) {
        if(lhs.eval(interpretation) == TruthValue.TRUE || rhs.eval(interpretation) == TruthValue.TRUE) {
            return TruthValue.TRUE;
        }
        else if(lhs.eval(interpretation) == TruthValue.FALSE && rhs.eval(interpretation) == TruthValue.FALSE) {
            return TruthValue.FALSE;
        }
        else {
            return TruthValue.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return "(" + this.lhs + ") ∨ (" + this.rhs + ")";
    }
}
