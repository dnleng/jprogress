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
    private long[] prevPerformance;
    private double prevQuality;

    public NaiveProgressor() {
        this.frontier = new LinkedList<>();
    }

    public NaiveProgressor(Formula input) {
        this.frontier = new LinkedList<>();
        this.frontier.add(input);
    }

    @Override
    public void progress(final Interpretation interpretation) {
        this.prevPerformance = new long[6];
        this.prevPerformance[0] = System.nanoTime();
        Set<Interpretation> reductions = interpretation.getReductions();
        this.prevQuality = 1.0 - (((double)reductions.size()-1.0) / (Math.pow(2, interpretation.getAtoms().size())-1.0));
        List<Formula> result = new LinkedList<>();
        this.prevPerformance[1] = System.nanoTime();
        for (Formula f : frontier) {
            for (Interpretation i : reductions) {
                result.add(f.progress(i).simplify().subsumption());
            }
        }
        this.frontier = result;
        this.prevPerformance[2] = System.nanoTime();
        this.prevPerformance[5] = System.nanoTime();
    }

    @Override
    public void set(final Formula input) {
        this.frontier = new LinkedList<>();
        this.frontier.add(input);
    }

    @Override
    public ProgressionStatus getStatus() {
        List<Node> nodeList = new LinkedList<>();

        Histogram hist = get();
        for (Formula key : hist.keySet()) {
            nodeList.add(new Node(key, new HashSet<>(), hist.get(key) / hist.getCount(), false, 0));
        }

        Collections.sort(nodeList);

        return new ProgressionStatus(nodeList, 0.0, this.prevPerformance, this.prevQuality);
    }

    @Override
    public ProgressorProperties getProperties() {
        int componentCount = 0;
        for(Formula f : frontier) {
            componentCount += f.getSize();
        }

        return new ProgressorProperties(ProgressionStrategy.NAIVE, 0, this.frontier.size(), componentCount, 1, Integer.MAX_VALUE);
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
