package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.*;
import se.liu.ida.jprogress.progressor.MProgressNaive;
import se.liu.ida.jprogress.progressor.graph.ProgressionGraph;
import se.liu.ida.jprogress.progressor.graph.ProgressionStrategy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by dnleng on 30/04/18.
 */
public class Main {
    private static final int ITERATIONS = 10;


    public static void main(String[] args) {
//        runExp1();
//        runExp2();
//        runExp3();
//        runExp4();
//        runExp5();
//        runExp6();
        runExp7();
    }

    private static void runExp1() {
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

    private static void runExp2() {
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

    private static void runExp3() {
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

    private static void runExp4() {
        Formula f4 = FormulaFactory.createFormula(FormulaTemplate.APEQ);
        MProgressNaive progressor = new MProgressNaive(f4);
        Interpretation i4 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));
        System.out.println("Formula: " + f4);
        for (int i = 0; i < ITERATIONS; i++) {
            progressor.progress(i4);
        }
        System.out.println("Histogram:");
        System.out.println(progressor.get());

        System.out.println();
    }

    private static void runExp5() {
        System.out.println("EXPERIMENT 5");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f5 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        Interpretation i5 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph5 = new ProgressionGraph(ProgressionStrategy.GRAPH, f5);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End-t1Start) + "ms");
//        System.out.println(graph5.getMassStatus());
        for (int i = 0; i < ITERATIONS; i++) {
//            System.out.println("Progression iteration: " + (i + 1));
            long t2Start = System.currentTimeMillis();
            graph5.progress(i5);
            long t2End = System.currentTimeMillis();
//            System.out.println(graph5.getMassStatus());
//            System.out.println(graph5.getGraphStatus());
//            System.out.println("Progression time: " + (t2End-t2Start) + "ms\n");
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph5.getMassStatus());
        System.out.println(graph5.getGraphStatus());
        System.out.println("Total runtime: " + (tEnd-tStart) + "ms\n");
    }

    private static void runExp6() {
        System.out.println("EXPERIMENT 6");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f6 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        Interpretation i6 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph6 = new ProgressionGraph(ProgressionStrategy.ONLINE, f6);
//        graph6.setTTL(2);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End-t1Start) + "ms");
//        System.out.println(graph6.getMassStatus(0.001));
        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println("Progression iteration: " + (i + 1));
            long t2Start = System.currentTimeMillis();;
            graph6.progress(i6);
            long t2End = System.currentTimeMillis();
//            System.out.println(graph6.getMassStatus());
//            System.out.println(graph6.getGraphStatus());
//            System.out.println("Progression time: " + (t2End-t2Start) + "ms\n");
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph6.getMassStatus());
        System.out.println(graph6.getGraphStatus());
        System.out.println("Total runtime: " + (tEnd-tStart) + "ms\n");
    }

    private static void runExp7() {
        System.out.println("EXPERIMENT 7");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f6 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        Interpretation i6 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));
        System.out.println("Progressing "+f6);

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph6 = new ProgressionGraph(ProgressionStrategy.ONLINE, f6);
//        graph6.setTTL(2);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End-t1Start) + "ms");
        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println("Progression iteration: " + (i + 1));
            long t2Start = System.currentTimeMillis();;
            graph6.progress(i6);
            long t2End = System.currentTimeMillis();
            System.out.println(graph6.getMassStatus());
            System.out.println(graph6.getGraphStatus());
        }
        long tEnd = System.currentTimeMillis();

        System.out.println("Total runtime: " + (tEnd-tStart) + "ms\n");
    }
}
