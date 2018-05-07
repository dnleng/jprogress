package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Bottom extends Atom {

    private static Bottom INSTANCE;

    private Bottom() {
        super("⊥");
    }

    public static Bottom getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Bottom();
        }

        return INSTANCE;
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
        return TruthValue.FALSE;
    }
}
