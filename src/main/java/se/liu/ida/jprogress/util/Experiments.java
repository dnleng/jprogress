package se.liu.ida.jprogress.util;

import se.liu.ida.jprogress.Executor;
import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.Logger;
import se.liu.ida.jprogress.Main;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.progressor.NaiveProgressor;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.progressor.Progressor;
import se.liu.ida.jprogress.progressor.ProgressorFactory;
import se.liu.ida.jprogress.progressor.graph.ProgressionGraph;
import se.liu.ida.jprogress.stream.ComplexGenerator;
import se.liu.ida.jprogress.stream.StreamGenerator;
import se.liu.ida.jprogress.stream.UnknownGenerator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Squig on 06/05/2018.
 */
public class Experiments {
    private static final int ITERATIONS = 1500;

    private Experiments() {
    }

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

            f2 = f2.progress(i2).simplify().subsumption();
            ;
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
        System.out.println("Setup time: " + (t1End - t1Start) + "ms");
        for (int i = 0; i < ITERATIONS; i++) {
            graph5.progress(i5);
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph5.getMassStatus(0.0001));
        System.out.println(graph5.getProperties());
        System.out.println("Total runtime: " + (tEnd - tStart) + "ms\n");
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
        System.out.println("Setup time: " + (t1End - t1Start) + "ms");
        for (int i = 0; i < ITERATIONS; i++) {
            graph6.progress(i6);
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph6.getMassStatus(0.0001));
        System.out.println(graph6.getProperties());
        System.out.println("Total runtime: " + (tEnd - tStart) + "ms\n");
    }

    public static void runExp7() {
        System.out.println("EXPERIMENT 7");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f6 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        Interpretation i6 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));
        System.out.println("Progressing " + f6);

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph6 = new ProgressionGraph(ProgressionStrategy.ONLINE, f6);
        graph6.setTTL(5);
        graph6.setMaxNodes(100);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End - t1Start) + "ms");
        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println("Progression iteration: " + (i + 1));
            graph6.progress(i6);
        }
        long tEnd = System.currentTimeMillis();

        System.out.println(graph6.getMassStatus(0.0001));
        System.out.println(graph6.getProperties());
        System.out.println("Total runtime: " + (tEnd - tStart) + "ms\n");
    }

    public static void runExp8() {
        System.out.println("EXPERIMENT 8");
        System.out.println("============");
        long tStart = System.currentTimeMillis();
        Formula f6 = FormulaFactory.createFormula(FormulaTemplate.BIG_APEQ);
        System.out.println("Progressing " + f6);

        long t1Start = System.currentTimeMillis();
        ProgressionGraph graph6 = new ProgressionGraph(ProgressionStrategy.ONLINE, f6);
//        graph6.setTTL(5);
        graph6.setMaxNodes(12);
        long t1End = System.currentTimeMillis();
        System.out.println("Setup time: " + (t1End - t1Start) + "ms");
        Executor executor = new Executor(graph6, new UnknownGenerator(Arrays.asList("p", "q")), 0.9995);
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
        System.out.println("Total runtime: " + (tEnd - tStart) + "ms\n");
    }

    public static void runFaultyAEP(int maxRepeats, double faultRatio, boolean verbose) {
        long t1Start = System.currentTimeMillis();
        ProgressorFactory pf = new ProgressorFactory();
        Progressor progressor = pf.create(FormulaFactory.createAEP(10), ProgressionStrategy.ONLINE);
        StreamGenerator generator = StreamPatterns.createAlteratingFalseTrue("p", 10, 1, maxRepeats, faultRatio);
        long t1End = System.currentTimeMillis();
        System.out.println("Formula: " + FormulaFactory.createAEP(10).toString());
        System.out.println("Setup time: " + (t1End - t1Start) + "ms\n");
        Executor executor = new Executor(progressor, generator, 0.99, verbose);
        executor.start();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tEnd = System.currentTimeMillis();

        System.out.println("RESULT");
        System.out.println(progressor.getStatus());
        System.out.println(progressor.getProperties());
        System.out.println("Total iterations: " + executor.getIteration());
        System.out.println("Total runtime: " + (tEnd - t1Start) + "ms\n");
    }

    public static void runFaultyBernoulli(int maxRepeats, double faultRatio, boolean verbose) {
        long t1Start = System.nanoTime();
        ProgressorFactory pf = new ProgressorFactory();
        Progressor progressor = pf.create(FormulaFactory.createBernoulli(Integer.MAX_VALUE), ProgressionStrategy.ONLINE);
        StreamGenerator generator = StreamPatterns.createConstant("p", false, maxRepeats, faultRatio, Main.SEED);
        long t1End = System.nanoTime();
        System.out.println("Formula: " + FormulaFactory.createAEP(10).toString());
        System.out.println("Setup time: " + Math.round(((double)t1End - (double)t1Start)/1000.0/1000.0) + "ms\n");
        Executor executor = new Executor(progressor, generator, 0.9995, verbose);
        executor.start();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tEnd = System.nanoTime();

        System.out.println("RESULT");
        System.out.println(progressor.getStatus());
        System.out.println(progressor.getProperties());
        System.out.println("Total iterations: " + executor.getIteration());
        System.out.println("Total runtime: " + Math.round(((double)tEnd - (double)t1Start)/1000.0/1000.0) + "ms\n");
    }

    public static void runFaultyBernoulli2(int maxRepeats, double faultRatio, int ttl, int maxNodes, boolean verbose) {
        long t1Start = System.nanoTime();
        ProgressorFactory pf = new ProgressorFactory();
        pf.setMaxNodes(maxNodes);
        pf.setMaxTTL(ttl);
        Progressor progressor = pf.create(FormulaFactory.createBernoulli(10000), ProgressionStrategy.LEAKY);
        StreamGenerator generator = StreamPatterns.createConstant("p", false, maxRepeats, faultRatio, Main.SEED);
        long t1End = System.nanoTime();
        System.out.println("Formula: " + FormulaFactory.createAEP(10).toString());
        System.out.println("Setup time: " + Math.round(((double)t1End - (double)t1Start)/1000.0/1000.0) + "ms\n");
        Executor executor = new Executor(progressor, generator, 0.9995, verbose);
        executor.start();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tEnd = System.nanoTime();

        System.out.println("RESULT");
        System.out.println(progressor.getStatus());
        System.out.println(progressor.getProperties());
        System.out.println("Total iterations: " + executor.getIteration());
        System.out.println("Total runtime: " + Math.round(((double)tEnd - (double)t1Start)/1000.0/1000.0) + "ms\n");
    }


    public static void runFaultyLeashing(int maxRepeats, double faultRatio, int ttl, int maxNodes, boolean verbose) {
        long t1Start = System.nanoTime();
        ProgressorFactory pf = new ProgressorFactory();
        pf.setMaxNodes(maxNodes);
        pf.setMaxTTL(ttl);
        Progressor progressor = pf.create(FormulaFactory.createTypeB(Integer.MAX_VALUE, 100), ProgressionStrategy.LEAKY);
        //StreamGenerator generator = StreamPatterns.createAlteratingFalseTrue("p", 5000, 1, maxRepeats, faultRatio);
        StreamGenerator generator = StreamPatterns.createConstant("p", true, maxRepeats, faultRatio, Main.SEED);
        long t1End = System.nanoTime();
        System.out.println("Formula: " + FormulaFactory.createTypeB(Integer.MAX_VALUE, 100).toString());
        System.out.println("Setup time: " + Math.round(((double)t1End - (double)t1Start)/1000.0/1000.0) + "ms\n");
        Executor executor = new Executor(progressor, generator, 0.9995, verbose);
        executor.start();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tEnd = System.nanoTime();

        System.out.println("RESULT");
        System.out.println(progressor.getStatus());
        System.out.println(progressor.getProperties());
        System.out.println("Total iterations: " + executor.getIteration());
        System.out.println("Total runtime: " + Math.round(((double)tEnd - (double)t1Start)/1000.0/1000.0) + "ms\n");
    }

    public static void runFaultyTypeC(int maxRepeats, double faultRatio, int ttl, int maxNodes, boolean verbose) {
        long t1Start = System.nanoTime();
        ProgressorFactory pf = new ProgressorFactory();
        pf.setMaxNodes(maxNodes);
        pf.setMaxTTL(ttl);
        Progressor progressor = pf.create(FormulaFactory.createTypeC(Integer.MAX_VALUE, 100, 10), ProgressionStrategy.LEAKY);
        StreamGenerator generator = StreamPatterns.createConstant("p", true, maxRepeats, faultRatio, Main.SEED);
        long t1End = System.nanoTime();
        System.out.println("Formula: " + FormulaFactory.createTypeC(Integer.MAX_VALUE, 100, 10).toString());
        System.out.println("Setup time: " + Math.round(((double)t1End - (double)t1Start)/1000.0/1000.0) + "ms\n");
        Executor executor = new Executor(progressor, generator, 0.99, verbose);
        executor.start();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tEnd = System.nanoTime();

        System.out.println("RESULT");
        System.out.println(progressor.getStatus());
        System.out.println(progressor.getProperties());
        System.out.println("Total iterations: " + executor.getIteration());
        System.out.println("Total runtime: " + Math.round(((double)tEnd - (double)t1Start)/1000.0/1000.0) + "ms\n");
    }

    public static void runFaultTypeD(int maxRepeats, double faultRatio, int ttl, int maxNodes, boolean verbose) {
        long t1Start = System.nanoTime();
        ProgressorFactory pf = new ProgressorFactory();
        pf.setMaxNodes(maxNodes);
        pf.setMaxTTL(ttl);
        Progressor progressor = pf.create(FormulaFactory.createTypeD(10000, 4000), ProgressionStrategy.LEAKY);
        List<StreamGenerator> generatorList = new LinkedList<>();
        generatorList.add(StreamPatterns.createConstant("p", false, maxRepeats, faultRatio, Main.SEED));
        generatorList.add(StreamPatterns.createAlteratingTrueFalse("q", 399, 1, maxRepeats, 0.0));
        generatorList.add(StreamPatterns.createConstant("r", true, maxRepeats, faultRatio, Main.SEED));
        StreamGenerator generator = new ComplexGenerator(generatorList);
        long t1End = System.nanoTime();
        System.out.println("Formula: " + FormulaFactory.createTypeD(10000, 4000).toString());
        System.out.println("Setup time: " + Math.round(((double)t1End - (double)t1Start)/1000.0/1000.0) + "ms\n");
        Executor executor = new Executor(progressor, generator, 0.9995, verbose);
        executor.start();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long tEnd = System.nanoTime();

        System.out.println("RESULT");
        System.out.println(progressor.getStatus());
        System.out.println(progressor.getProperties());
        System.out.println("Total iterations: " + executor.getIteration());
        System.out.println("Total runtime: " + Math.round(((double)tEnd - (double)t1Start)/1000.0/1000.0) + "ms\n");
    }
}
