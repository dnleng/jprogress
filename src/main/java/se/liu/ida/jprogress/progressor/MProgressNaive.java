package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.Histogram;
import se.liu.ida.jprogress.progressor.graph.ProgressionStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Squig on 01/05/2018.
 */
public class MProgressNaive implements Progressor {

    private List<Formula> frontier;

    public MProgressNaive() {
        this.frontier = new LinkedList<>();
    }

    public MProgressNaive(Formula input) {
        this.frontier = new LinkedList<>();
        this.frontier.add(input);
    }

    @Override
    public void progress(final Interpretation interpretation) {
        Set<Interpretation> reductions = interpretation.getReductions();
        List<Formula> result = new LinkedList<>();
        for (Formula f : frontier) {
            for (Interpretation i : reductions) {
                result.add(f.progress(i).simplify().subsumption());
            }
        }

        this.frontier = result;
    }

    @Override
    public void set(final Formula input) {
        this.frontier = new LinkedList<>();
        this.frontier.add(input);
    }

    @Override public ProgressionStatus getStatus() {
        //TODO: Use get() to obtain histogram and calculate status
        return null;
    }

    public Histogram get() {
        Histogram hist = new Histogram();
        for (Formula f : frontier) {
            hist.add(f.toString());
        }
        return hist;
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
