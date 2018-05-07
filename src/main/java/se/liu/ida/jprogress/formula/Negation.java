package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Negation extends Formula {
    private Formula formula;

    public Negation(Formula formula) {
        super();
        this.formula = formula;
    }

    public Formula progress(Interpretation interpretation) {
        return new Negation(this.formula.progress(interpretation));
    }

    public TruthValue eval(Interpretation interpretation) {
        if (this.formula.eval(interpretation) == TruthValue.UNKNOWN) {
            return TruthValue.UNKNOWN;
        } else if (this.formula.eval(interpretation) == TruthValue.TRUE) {
            return TruthValue.FALSE;
        } else {
            return TruthValue.TRUE;
        }
    }

    @Override
    public Formula simplify() {
        this.formula = this.formula.simplify();

        if (formula instanceof Bottom) {
            return Top.getInstance();
        } else if (formula instanceof Top) {
            return Bottom.getInstance();
        }

        // Eliminate double negations
        if (this.formula instanceof Negation) {
            Negation child = (Negation) this.formula;
            return child.formula;
        }

        return this;
    }

    @Override
    public int getSize() {
        return 1 + formula.getSize();
    }

    public Set<String> getAtoms() {
        return formula.getAtoms();
    }

    @Override
    public String toString() {
        return "¬(" + this.formula + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Negation negation = (Negation) o;

        return formula.equals(negation.formula);
    }

    @Override
    public int hashCode() {
        return formula.hashCode();
    }
}
