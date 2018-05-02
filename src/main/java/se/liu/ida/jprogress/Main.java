package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.FormulaFactory;
import se.liu.ida.jprogress.formula.FormulaTemplate;
import se.liu.ida.jprogress.formula.TruthValue;
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
    public static void main(String[] args) {
        Interpretation i1 = new Interpretation();
        i1.setTruthValue("p", TruthValue.TRUE);
        i1.setTruthValue("q", TruthValue.FALSE);

        Formula f1 = FormulaFactory.createFormula(FormulaTemplate.APEQ);
        System.out.println("Formula: " + f1);
        while (f1.eval(i1) == TruthValue.UNKNOWN) {
            f1 = f1.progress(i1);
            System.out.println("Progressed (" + i1 + "): " + f1);
        }

        System.out.println();

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

            f2 = f2.progress(i2);
            System.out.println("Progressed (" + i2 + "): " + f2);
        }

        System.out.println();

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

        Formula f4 = FormulaFactory.createFormula(FormulaTemplate.APEQ);
        MProgressNaive progressor = new MProgressNaive(f4);
        Interpretation i4 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));
        System.out.println("Formula: " + f4);
        for (int i = 0; i < 10; i++) {
            progressor.progress(i4);
        }
        System.out.println("Histogram:");
        System.out.println(progressor.get());

        System.out.println();

        Formula f5 = FormulaFactory.createFormula(FormulaTemplate.APEQ);
        Interpretation i5 = Interpretation.buildFullyUnknown(Arrays.asList("p", "q"));

        ProgressionGraph graph = new ProgressionGraph(ProgressionStrategy.GRAPH, f5);
        System.out.println(graph.getMassStatus(0.001));
        for (int i = 0; i < 1000; i++) {
            System.out.println("Progression iteration: " + (i + 1));
            graph.progress(i5);
            System.out.println(graph.getMassStatus(0.001));
        }
    }
}
