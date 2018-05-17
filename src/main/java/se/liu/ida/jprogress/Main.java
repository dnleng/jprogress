package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.reasoning.DefaultTheory;
import se.liu.ida.jprogress.reasoning.HornTheory;
import se.liu.ida.jprogress.reasoning.Literal;
import se.liu.ida.jprogress.reasoning.Rule;
import se.liu.ida.jprogress.util.Experiments;

import java.util.Arrays;


public class Main {
    public static final int MAX_THREADS = 10;
    public static final long SEED = 1129384888971617300L;

    public static void main(String[] args) {
        final int RERUNS = 10;

        if(args.length == 0) {
            HornTheory gamma1 = new HornTheory();
            HornTheory gamma2 = new HornTheory();
            gamma2.addRule(new Rule(new Literal("q", TruthValue.TRUE), Arrays.asList(new Literal("p", TruthValue.TRUE))));
            HornTheory gamma3 = new HornTheory();
            gamma3.addRule(new Rule(new Literal("r", TruthValue.TRUE), Arrays.asList(new Literal("p", TruthValue.TRUE))));

            for(int i = 1; i < 10; i++) {
                Experiments.runTypeTwoChi(Integer.MAX_VALUE, 0.1*i, 1, Integer.MAX_VALUE, ProgressionStrategy.LEAKY, gamma1, "data/kr-exp-13/gamma1-"+i+".csv", false);
                Experiments.runTypeTwoChi(Integer.MAX_VALUE, 0.1*i, 1, Integer.MAX_VALUE, ProgressionStrategy.LEAKY, gamma2, "data/kr-exp-13/gamma2-"+i+".csv", false);
                Experiments.runTypeTwoChi(Integer.MAX_VALUE, 0.1*i, 1, Integer.MAX_VALUE, ProgressionStrategy.LEAKY, gamma3, "data/kr-exp-13/gamma3-"+i+".csv", false);
            }
        }
        else {
            // Hackish solution
            try {
                String path = args[0];
                boolean precompute = args[1].equals("true");

                int maxTTL = Integer.MAX_VALUE;
                if (!args[2].equals("inf")) {
                    maxTTL = Integer.parseInt(args[2]);
                }

                int maxNodes = Integer.MAX_VALUE;
                if (!args[3].equals("inf")) {
                    maxNodes = Integer.parseInt(args[3]);
                }

                double faultRatio = Integer.parseInt(args[4]) / 100.0;

                String formula = args[5];

                if(formula.equals("C")) {
                    System.out.println("Writing " + path);
                    Experiments.runFaultyTypeC(Integer.MAX_VALUE, faultRatio, maxTTL, maxNodes,
                            precompute ? ProgressionStrategy.OFFLINE : ProgressionStrategy.LEAKY, path, false);
                }
                else if(formula.equals("chi1")) {
                    System.out.println("Writing " + path);
                    HornTheory gamma1 = new HornTheory();
                    Experiments.runTypeTwoChi(Integer.MAX_VALUE, faultRatio, 1, Integer.MAX_VALUE, ProgressionStrategy.LEAKY, gamma1, path, false);
                }
                else if(formula.equals("chi2")) {
                    System.out.println("Writing " + path);
                    HornTheory gamma2 = new HornTheory();
                    gamma2.addRule(new Rule(new Literal("q", TruthValue.TRUE), Arrays.asList(new Literal("p", TruthValue.TRUE))));
                    Experiments.runTypeTwoChi(Integer.MAX_VALUE, faultRatio, 1, Integer.MAX_VALUE, ProgressionStrategy.LEAKY, gamma2, path, false);
                }
                else if(formula.equals("chi3")) {
                    System.out.println("Writing " + path);
                    HornTheory gamma3 = new HornTheory();
                    gamma3.addRule(new Rule(new Literal("r", TruthValue.TRUE), Arrays.asList(new Literal("p", TruthValue.TRUE))));
                    Experiments.runTypeTwoChi(Integer.MAX_VALUE, faultRatio, 1, Integer.MAX_VALUE, ProgressionStrategy.LEAKY, gamma3, path, false);
                }
                else {
                    System.err.println("Unexpected formula: " + formula);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done!");
    }


}
