package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Disjunction extends Formula {

    private Formula lhs;
    private Formula rhs;

    public Disjunction(Formula lhs, Formula rhs) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Formula progress(Interpretation interpretation) {
        return new Disjunction(lhs.progress(interpretation), rhs.progress(interpretation)).simplify(interpretation);
    }

    public TruthValue eval(Interpretation interpretation) {
        if (lhs.eval(interpretation) == TruthValue.TRUE || rhs.eval(interpretation) == TruthValue.TRUE) {
            return TruthValue.TRUE;
        } else if (lhs.eval(interpretation) == TruthValue.FALSE && rhs.eval(interpretation) == TruthValue.FALSE) {
            return TruthValue.FALSE;
        } else {
            return TruthValue.UNKNOWN;
        }
    }

    @Override
    public Formula simplify(Interpretation interpretation) {
        if (this.eval(interpretation) == TruthValue.FALSE) {
            return new Bottom();
        } else if (this.eval(interpretation) == TruthValue.TRUE) {
            return new Top();
        } else if (lhs.eval(interpretation) == TruthValue.FALSE && rhs.eval(interpretation) == TruthValue.UNKNOWN) {
            return rhs;
        } else if (lhs.eval(interpretation) == TruthValue.UNKNOWN && rhs.eval(interpretation) == TruthValue.FALSE) {
            return lhs;
        } else {
            return this;
        }
    }

    public Set<String> getAtoms() {
        Set<String> result = new HashSet<>();
        result.addAll(lhs.getAtoms());
        result.addAll(rhs.getAtoms());
        return result;
    }


    @Override
    public String toString() {
        return "(" + this.lhs + ") ∨ (" + this.rhs + ")";
    }
}
