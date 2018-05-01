package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by dnleng on 30/04/18.
 */
public class Atom extends Formula {

    private String label;

    public Atom(String label) {
        this.label = label;
    }

    public Formula progress(Interpretation interpretation) {
        return this;
    }

    public TruthValue eval(Interpretation interpretation) {
        TruthValue truthValue = interpretation.getTruthValue(this.label);
        if(truthValue == TruthValue.UNKNOWN) {
            System.err.println("Warning: Proposition " + label + " has unknown truth value; applying NAF");
            truthValue = TruthValue.FALSE;
        }
        return truthValue;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
