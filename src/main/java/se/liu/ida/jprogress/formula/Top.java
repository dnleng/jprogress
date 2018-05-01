package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Top extends Atom {

    public Top() {
        super("⊤");
    }

    @Override
    public Formula progress(Interpretation interpretation) {
        return this;
    }

    @Override
    public Set<String> getAtoms() {
        return new HashSet<>();
    }

    @Override
    public TruthValue eval(Interpretation interpretation) {
        return TruthValue.TRUE;
    }
}
