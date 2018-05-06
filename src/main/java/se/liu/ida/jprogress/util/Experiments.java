package se.liu.ida.jprogress.util;

import se.liu.ida.jprogress.Executor;
import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.progressor.NaiveProgressor;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.progressor.graph.ProgressionGraph;
import se.liu.ida.jprogress.stream.UnknownGenerator;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Squig on 06/05/2018.
 */
public class Experiments {
    private static final int ITERATIONS = 1500;

    private Experiments() {}

    public static void runExp1() {
        Interpretation i1 = new Interpretation();
        i1.setTruthValue("p", TruthValue.TRUE);
        i1.setTruthValue("q", TruthValue.FALSE);

        Formula f1 = FormulaFactory.createFormula(FormulaTemplate.APEQ);
        System.out.println("Formula: " + f1);
        while (f1.eval(i1) == TruthValue.UNKNOWN) {
            f1 = f1.progress(i1).simplify().subsumption();
            System.out.println("Progressed (" + i1 + "): " + f1);
        }

        System.out.println();
    }

    public static void runExp2() {
        Interpretation i1 = new Interpretation();
        i1.setTruthValue("p", TruthValue.TRUE);
        i1.setTruthValue("q", TruthValue.FALSE);

        Random rand = new Random();
        Formula f2 = FormulaFactory.createFormula(FormulaTemplate.BERNOULLI);
        System.out.println("Formula: " + f2);
        while (f2.eval(i1) == TruthValue.UNKNOWN) {
            Interpretation i2 = new Interpretation();
            switch (rand.nextInt(2)) {
                case 0:
                    i2.setTruthValue("p", TruthValue.TRUE);
                    break;
                case 1:
                    i2.setTruthValue("p", TruthValue.FALSE);
                    break;
                default:
                    i2.setTruthValue("p", TruthValue.UNKNOWN);
                    break;
            }

            f2 = f2.progress(i2).simplify().subsumption();;
            System.out.println("Progressed (" + i2 + "): " + f2);
        }

        System.out.println();
    }

    public static void runExp3() {
        Interpretation i3 = new Interpretation();
        i3.setTruthValue("p", TruthValue.TRUE);
        i3.setTruthValue("q", TruthValue.UNKNOWN);
        i3.setTruthValue("r", TruthValue.UNKNOWN);
        i3.setTruthValue("s", TruthValue.FALSE);
        i3.setTruthValue("t", TruthValue.UNKNOWN);
        System.out.println("Interpretation: " + i3);
        for (Interpretation i : i3.getReductions()) {
            System.out.println("Reduction: " + i);
        }

        System.out.println();
    }

    public static void runExp4() {
        Formula f4 = FormulaFactory.createFormula(FormulaTemplate.APEQ);
        NaiveProgressor progressor = new NaiveProgressor(f4);
        Interpretation i4 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));
        System.out.println("Formula: " + f4);
        for (int i = 0; i < ITERATIONS; i++) {
            progressor.progress(i4);
        }
        System.out.println("Histogram:");
        System.out.println(progressor.get());

        System.out.println();
    }

    public static void runExp5() {
        System.out.println("EXPERIMENT 5");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f5 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        Interpretation i5 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph5 = new ProgressionGraph(ProgressionStrategy.OFFLINE, f5);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End-t1Start) + "ms");
        for (int i = 0; i < ITERATIONS; i++) {
            graph5.progress(i5);
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph5.getMassStatus(0.0001));
        System.out.println(graph5.getProperties());
        System.out.println("Total runtime: " + (tEnd-tStart) + "ms\n");
    }

    public static void runExp6() {
        System.out.println("EXPERIMENT 6");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f6 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        Interpretation i6 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph6 = new ProgressionGraph(ProgressionStrategy.ONLINE, f6);
        graph6.setTTL(1);
//        graph6.setMaxNodes(25);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End-t1Start) + "ms");
        for (int i = 0; i < ITERATIONS; i++) {
            graph6.progress(i6);
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph6.getMassStatus(0.0001));
        System.out.println(graph6.getProperties());
        System.out.println("Total runtime: " + (tEnd-tStart) + "ms\n");
    }

    public static void runExp7() {
        System.out.println("EXPERIMENT 7");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f6 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        Interpretation i6 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));
        System.out.println("Progressing "+f6);

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph6 = new ProgressionGraph(ProgressionStrategy.ONLINE, f6);
        graph6.setTTL(5);
        graph6.setMaxNodes(100);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End-t1Start) + "ms");
        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println("Progression iteration: " + (i + 1));
            graph6.progress(i6);
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph6.getMassStatus(0.0001));
        System.out.println(graph6.getProperties());
        System.out.println("Total runtime: " + (tEnd-tStart) + "ms\n");
    }

    public static void runExp8() {
        System.out.println("EXPERIMENT 8");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f6 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        System.out.println("Progressing "+f6);

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph6 = new ProgressionGraph(ProgressionStrategy.ONLINE, f6);
        graph6.setTTL(5);
        graph6.setMaxNodes(100);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End-t1Start) + "ms");
        Executor executor = new Executor(graph6, new UnknownGenerator(Arrays.asList("p", "q")), 0.99);
        executor.start();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph6.getMassStatus(0.0001));
        System.out.println(graph6.getProperties());
        System.out.println("Total iterations: " + executor.getIteration());
        System.out.println("Total runtime: " + (tEnd-tStart) + "ms\n");
    }
}
