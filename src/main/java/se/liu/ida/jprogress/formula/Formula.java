package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by dnleng on 30/04/18.
 */
public abstract class Formula {

    private UUID id;

    public Formula() {
//        this.setId(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public abstract Formula progress(Interpretation interpretation);

    public abstract TruthValue eval(Interpretation interpretation);

    public abstract Formula simplify(Interpretation interpretation);



    public Formula subsumption(Interpretation interpretation) {
        // Handle temporal subsumptions
        return this;
    }
//    {
//        if (this.eval(interpretation) == TruthValue.TRUE) {
//            return new Top();
//        } else if (this.eval(interpretation) == TruthValue.FALSE) {
//            return new Bottom();
//        } else {
//            return this;
//        }
//    }

    public abstract Set<String> getAtoms();

    @Override
    public abstract boolean equals(Object o);
}
