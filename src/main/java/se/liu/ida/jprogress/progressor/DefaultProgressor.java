package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.progressor.graph.Node;
import se.liu.ida.jprogress.progressor.graph.Transition;

import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Squig on 01/05/2018.
 */
public class DefaultProgressor implements Progressor {

    private Formula input;

    public DefaultProgressor() {
    }

    public DefaultProgressor(Formula input) {
        this.input = input;
    }

    @Override
    public void progress(final Interpretation interpretation) {
        if (this.input != null) {
            this.input = this.input.progress(interpretation);
        }
    }

    @Override
    public void set(final Formula input) {
        this.input = input;
    }

    @Override public ProgressionStatus getStatus() {
        return new ProgressionStatus(Collections.singletonList(new Node(this.input, new HashSet<>(), 1.0, false, 0)), 0.0);
    }

    @Override public ProgressorProperties getProperties() {
        return new ProgressorProperties(ProgressionStrategy.DEFAULT, 0, 1, Formula.getCount(), 1, 1);
    }

    public Formula get() {
        return this.input;
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
