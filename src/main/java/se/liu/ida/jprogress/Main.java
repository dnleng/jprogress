package se.liu.ida.jprogress;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import se.liu.ida.jprogress.distribution.UniformDistribution;
import se.liu.ida.jprogress.formula.*;
import se.liu.ida.jprogress.progressor.ProgressionStrategy;
import se.liu.ida.jprogress.progressor.Progressor;
import se.liu.ida.jprogress.progressor.ProgressorFactory;
import se.liu.ida.jprogress.stream.StreamGenerator;
import se.liu.ida.jprogress.parser.*;
import se.liu.ida.jprogress.util.StreamPatterns;


public class Main {
    public static final int MAX_THREADS = 12;
    public static final long SEED = 1129384888971617300L;

    public static void main(String[] args) {
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

            String formulaStr = args[5];
            MTLLexer lexer = new MTLLexer(new ANTLRInputStream(formulaStr));
            MTLParser parser = new MTLParser(new CommonTokenStream(lexer));
            ParseTree tree = parser.start();
            Formula formula = new MTLVisitorImpl().visit(tree);

            // Run progression
            long t1Start = System.nanoTime();
            ProgressorFactory pf = new ProgressorFactory();
            pf.setMaxNodes(maxNodes);
            pf.setMaxTTL(maxTTL);
            Progressor progressor = pf.create(formula, new UniformDistribution(formula.getAtoms()), precompute ? ProgressionStrategy.OFFLINE : ProgressionStrategy.LEAKY);
            StreamGenerator generator = StreamPatterns.createConstant("p", true, Integer.MAX_VALUE, faultRatio, Main.SEED);
            long t1End = System.nanoTime();
            System.out.println("Formula: " + formula.toString());
            System.out.println("Setup time: " + Math.round(((double)t1End - (double)t1Start)/1000.0/1000.0) + "ms\n");
            Executor executor = new Executor(progressor, generator, 0.99, path, false);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
