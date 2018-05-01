package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.Formula;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    @Override public void progress(final Interpretation interpretation) {
	Set<Interpretation> reductions = interpretation.getReductions();
        List<Formula> result = new LinkedList<>();
        for(Formula f : frontier) {
	    for (Interpretation i : reductions) {
		result.add(f.progress(i));
	    }
	}

	this.frontier = result;
    }

    @Override public void set(final Formula input) {
	this.frontier = new LinkedList<>();
	this.frontier.add(input);
    }

    public FormulaHistogram get() {
        FormulaHistogram hist = new FormulaHistogram();
	for(Formula f : frontier) {
	    hist.add(f.toString());
	}
        return hist;
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
