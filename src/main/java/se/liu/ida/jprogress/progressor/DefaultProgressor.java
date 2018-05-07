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
    private long[] prevPerformance;
    private double prevQuality;

    public DefaultProgressor() {
    }

    public DefaultProgressor(Formula input) {
        this.input = input;
    }

    @Override
    public void progress(final Interpretation interpretation) {
        this.prevPerformance = new long[6];
        this.prevPerformance[0] = System.nanoTime();
        this.prevQuality = 1.0 - (((double)interpretation.getReductions().size()-1.0) / (Math.pow(2, interpretation.getAtoms().size())-1.0));

        if (this.input != null) {
            this.prevPerformance[1] = System.nanoTime();
            this.input = this.input.progress(interpretation);
            this.prevPerformance[2] = System.nanoTime();
        }
        this.prevPerformance[5] = System.nanoTime();
    }

    @Override
    public void set(final Formula input) {
        this.input = input;
    }

    @Override
    public ProgressionStatus getStatus() {
        return new ProgressionStatus(Collections.singletonList(new Node(this.input, new HashSet<>(), 1.0, false, 0)), 0.0, this.prevPerformance, this.prevQuality);
    }

    @Override
    public ProgressorProperties getProperties() {
        return new ProgressorProperties(ProgressionStrategy.DEFAULT, 0, 1, input.getSize(), 1, 1);
    }

    public Formula get() {
        return this.input;
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
