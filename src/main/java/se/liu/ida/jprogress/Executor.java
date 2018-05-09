package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.progressor.Progressor;
import se.liu.ida.jprogress.progressor.ProgressionStatus;
import se.liu.ida.jprogress.progressor.ProgressorProperties;
import se.liu.ida.jprogress.stream.StreamGenerator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Squig on 06/05/2018.
 */
public class Executor extends Thread {

    private Progressor progressor;
    private StreamGenerator generator;
    private double terminator;
    private boolean running;
//    private List<ProgressionStatus> statusList;
//    private List<ProgressorProperties> propertiesList;
    private boolean verbose;
    private int iteration;
    private String path;

    public Executor(Progressor progressor, StreamGenerator generator, double terminator, String path, boolean verbose) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = terminator;
        this.running = false;
        this.verbose = verbose;
        this.iteration = 0;
        this.path = path;
    }

    public Executor(Progressor progressor, StreamGenerator generator, String path, double terminator) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = terminator;
        this.running = false;
        this.verbose = false;
        this.iteration = 0;
        this.path = path;
    }

    public Executor(Progressor progressor, StreamGenerator generator) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = 1.0;
        this.running = false;
        this.verbose = false;
        this.iteration = 0;
        this.path = "latest.csv";
    }


    @Override
    public void run() {
        this.running = true;
        Logger logger = new Logger(this.path);
        while (generator.hasNext()) {
            if (verbose) {
                System.out.println("Iteration " + this.iteration);
            }

            progressor.progress(generator.next());

            ProgressionStatus status = progressor.getStatus();
            logger.write(status, progressor.getProperties());

            if (verbose) {
                System.out.println(status);
                System.out.println(progressor.getProperties());
            }

            if (status.getTrueVerdict() + status.getFalseVerdict() + status.getUnknownVerdict() >= terminator) {
                break;
            }

            this.iteration++;
        }
        this.running = false;
    }

    public synchronized boolean isRunning() {
        return this.running;
    }

    public int getIteration() {
        return this.iteration;
    }
}
