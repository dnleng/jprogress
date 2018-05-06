package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.util.Histogram;
import se.liu.ida.jprogress.progressor.graph.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Squig on 01/05/2018.
 */
public class NaiveProgressor implements Progressor {

    private List<Formula> frontier;

    public NaiveProgressor() {
        this.frontier = new LinkedList<>();
    }

    public NaiveProgressor(Formula input) {
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
        List<Node> nodeList = new LinkedList<>();

        Histogram hist = get();
        for(Formula key : hist.keySet()) {
            nodeList.add(new Node(key, new HashSet<>(), hist.get(key)/hist.getCount(), false, 0));
        }

        Collections.sort(nodeList);

        return new ProgressionStatus(nodeList, 0.0);
    }

    @Override public ProgressorProperties getProperties() {
        return new ProgressorProperties(ProgressionStrategy.NAIVE, 0, this.frontier.size(), Formula.getCount(), 1, Integer.MAX_VALUE);
    }

    public Histogram get() {
        Histogram hist = new Histogram();
        for (Formula f : frontier) {
            hist.add(f);
        }
        return hist;
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
