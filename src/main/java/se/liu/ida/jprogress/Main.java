package se.liu.ida.jprogress;

import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.util.Experiments;


public class Main {
    public static final int MAX_THREADS = 10;
    public static final long SEED = 1129384888971617300L;

    public static void main(String[] args) {
        final int RERUNS = 10;

//        for(int i = 0; i < RERUNS; i++) {
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, Integer.MAX_VALUE, Integer.MAX_VALUE, ProgressionStrategy.OFFLINE, "kr-offline-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 1, 175, ProgressionStrategy.LEAKY, "kr-online-1-175-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 1, 200, ProgressionStrategy.LEAKY, "kr-online-1-200-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 1, 225, ProgressionStrategy.LEAKY, "kr-online-1-225-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 1, 250, ProgressionStrategy.LEAKY, "kr-online-1-250-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 1, Integer.MAX_VALUE, ProgressionStrategy.ONLINE, "kr-online-1-inf-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 5, 175, ProgressionStrategy.LEAKY, "kr-online-5-175-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 5, 200, ProgressionStrategy.LEAKY, "kr-online-5-200-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 5, 225, ProgressionStrategy.LEAKY, "kr-online-5-225-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 5, 250, ProgressionStrategy.LEAKY, "kr-online-5-250-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 5, Integer.MAX_VALUE, ProgressionStrategy.ONLINE, "kr-online-5-inf-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, Integer.MAX_VALUE, 175, ProgressionStrategy.LEAKY, "kr-online-inf-175-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, Integer.MAX_VALUE, 200, ProgressionStrategy.LEAKY, "kr-online-inf-200-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, Integer.MAX_VALUE, 225, ProgressionStrategy.LEAKY, "kr-online-inf-225-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, Integer.MAX_VALUE, 250, ProgressionStrategy.LEAKY, "kr-online-inf-250-"+(i+1)+".csv", false);
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, Integer.MAX_VALUE, Integer.MAX_VALUE, ProgressionStrategy.ONLINE, "kr-online-inf-inf-"+(i+1)+".csv", false);
//        }

        // Hackish solution
        try {
            String path = args[0];
            boolean precompute = args[1].equals("true");
            int maxTTL = Integer.parseInt(args[2]);
            int maxNodes = Integer.parseInt(args[3]);

            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, maxTTL, maxNodes,
                                       precompute ? ProgressionStrategy.OFFLINE : ProgressionStrategy.LEAKY, path, false);
        } catch(Exception e) {
            e.printStackTrace();
        }

//        for(int i = 50; i < 150; i++) {
//            Experiments.runFaultyTypeC(Integer.MAX_VALUE, 0.2, 1, 75, ProgressionStrategy.LEAKY, "kr-online-1-"+(i)+".csv", false);
//        }

        System.out.println("Done!");
    }


}
