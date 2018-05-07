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
    public static final int MAX_THREADS = 8;

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
        Experiments.runFaultyBernoulli(Integer.MAX_VALUE, 0.1, false);
    }


}
