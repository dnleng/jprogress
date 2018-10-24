package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.*;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.progressor.Progressor;
import se.liu.ida.jprogress.progressor.ProgressorFactory;
import se.liu.ida.jprogress.stream.StreamGenerator;
import se.liu.ida.jprogress.util.StreamPatterns;

public class Main {
    public static final int MAX_THREADS = 10;
    public static final long SEED = 1129384888971617300L;

    public static void main(String[] args) {
        final int RERUNS = 10;

        for(int i = 0; i < RERUNS; i++) {
            for (int maxNodes = 5; maxNodes < 190; maxNodes++) {
                // Construct a progressor factory
                long t1Start = System.nanoTime();
                ProgressorFactory pf = new ProgressorFactory();
                pf.setMaxNodes(maxNodes);
                pf.setMaxTTL(1);

                // Build an MTL formula and feed it to a progressor
                Atom p = new Atom("p");
                Formula f = new Always(Integer.MAX_VALUE, new Disjunction(p, new Eventually(100, new Always(10, p))));
                Progressor progressor = pf.create(f, ProgressionStrategy.LEAKY);

                // Configure a stream generator with a stream pattern
                StreamGenerator generator = StreamPatterns.createConstant("p", true, Integer.MAX_VALUE, 0.2, Main.SEED);
                long t1End = System.nanoTime();
                System.out.println("Formula: " + f.toString());
                System.out.println("Setup time: " + Math.round(((double) t1End - (double) t1Start) / 1000.0 / 1000.0) + "ms\n");

                // Run partial-state progression
                Executor executor = new Executor(progressor, generator, 0.99, "kr-online-1-" + maxNodes + "-" + i + ".csv", false);
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
                System.out.println("Total runtime: " + Math.round(((double) tEnd - (double) t1Start) / 1000.0 / 1000.0) + "ms\n");
            }
        }
    }
}
