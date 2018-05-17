package se.liu.ida.jprogress;

import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.reasoning.DefaultTheory;
import se.liu.ida.jprogress.util.Experiments;


public class Main {
    public static final int MAX_THREADS = 10;
    public static final long SEED = 1129384888971617300L;

    public static void main(String[] args) {
        final int RERUNS = 10;

        if(args.length == 0) {
            Experiments.runTypeTwoChi(Integer.MAX_VALUE, 0.2, 1, Integer.MAX_VALUE, ProgressionStrategy.LEAKY, new DefaultTheory(), "latest.csv", true);
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

                System.out.println("Writing " + path);
                Experiments.runFaultyTypeC(Integer.MAX_VALUE, faultRatio, maxTTL, maxNodes,
                        precompute ? ProgressionStrategy.OFFLINE : ProgressionStrategy.LEAKY, path, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done!");
    }


}
