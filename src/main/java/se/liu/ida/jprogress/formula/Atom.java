package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Atom extends Formula {

    private String label;

    public Atom(String label) {
        super();
        this.label = label;
    }

    public Formula progress(Interpretation interpretation) {
        if (this.eval(interpretation) == TruthValue.TRUE) {
            return Top.getInstance();
        } else if (this.eval(interpretation) == TruthValue.FALSE) {
            return Bottom.getInstance();
        } else {
            return this;
        }
    }

    public TruthValue eval(Interpretation interpretation) {
        TruthValue truthValue = interpretation.getTruthValue(this.label);
        if (truthValue == TruthValue.UNKNOWN) {
            System.err.println("Warning: Proposition " + label + " has unknown truth value; applying NAF");
            truthValue = TruthValue.FALSE;
        }
        return truthValue;
    }

    @Override
    public Formula simplify() {
        return this;
    }

    @Override
    public int getSize() {
        return 1;
    }

    public Set<String> getAtoms() {
        Set<String> result = new HashSet<>();
        result.add(this.label);
        return result;
    }

    @Override
    public String toString() {
        return this.label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Atom atom = (Atom) o;

        return label.equals(atom.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
