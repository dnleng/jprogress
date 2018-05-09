package se.liu.ida.jprogress;

import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.progressor.Progressor;
import se.liu.ida.jprogress.progressor.ProgressorFactory;
import se.liu.ida.jprogress.progressor.graph.ProgressionGraph;
import se.liu.ida.jprogress.stream.StreamGenerator;
import se.liu.ida.jprogress.stream.UnknownGenerator;
import se.liu.ida.jprogress.util.Experiments;
import se.liu.ida.jprogress.util.FormulaFactory;
import se.liu.ida.jprogress.util.StreamPatterns;

import java.util.Arrays;


public class Main {
    public static final int MAX_THREADS = 10;
    public static final long SEED = 1129384888971617300L;

    public static void main(String[] args) {
//        Experiments.runExp1();
//        Experiments.runExp2();
//        Experiments.runExp3();
//        Experiments.runExp4();
//        Experiments.runExp5();
//        Experiments.runExp6();
//        Experiments.runExp7();
//        Experiments.runExp8();
//        Experiments.runFaultyAEP(Integer.MAX_VALUE, 0.5, true);
//        Experiments.runFaultyBernoulli2(Integer.MAX_VALUE, 0.0001, Integer.MAX_VALUE, 2, false);
        Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 1, 150, false);
//        Experiments.runFaultTypeD(Integer.MAX_VALUE, 0.01, Integer.MAX_VALUE, 15, true);
    }


}
