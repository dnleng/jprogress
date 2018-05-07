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
    private static int count = 0;

    public Formula() {
        count++;
    }

    public static int getCount() {
        return count;
    }

    public static void resetCount() {
        count = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public abstract Formula progress(Interpretation interpretation);

    public abstract TruthValue eval(Interpretation interpretation);

    public abstract Formula simplify();


    public Formula subsumption() {
        // Handle temporal subsumptions
        return this;
    }

    public Formula progressOnce(Interpretation i) {
        return progress(i).simplify().subsumption();
    }

    public abstract Set<String> getAtoms();

    @Override
    public abstract boolean equals(Object o);
}
