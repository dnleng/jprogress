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
        switch(eval(interpretation)) {
            case TRUE:
                return new Top();
            case FALSE:
                return new Bottom();
            default:
                System.err.println("Missing Boolean truth value for proposition "+this.label+": Applying NAF");
                return new Bottom();
        }
    }

    public TruthValue eval(Interpretation interpretation) {
        return interpretation.getTruthValue(this.label);
    }

    @Override
    public String toString() {
        return this.label;
    }
}
