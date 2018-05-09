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

    public Executor(Progressor progressor, StreamGenerator generator, double terminator, boolean verbose) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = terminator;
        this.running = false;
//        this.statusList = new LinkedList<>();
//        this.propertiesList = new LinkedList<>();
        this.verbose = verbose;
        this.iteration = 0;
    }

    public Executor(Progressor progressor, StreamGenerator generator, double terminator) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = terminator;
        this.running = false;
//        this.statusList = new LinkedList<>();
//        this.propertiesList = new LinkedList<>();
        this.verbose = false;
        this.iteration = 0;
    }

    public Executor(Progressor progressor, StreamGenerator generator) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = 1.0;
        this.running = false;
//        this.statusList = new LinkedList<>();
//        this.propertiesList = new LinkedList<>();
        this.verbose = false;
        this.iteration = 0;
    }


    @Override
    public void run() {
        this.running = true;
        Logger logger = new Logger("latest.csv");
        while (generator.hasNext()) {
            if (verbose) {
                System.out.println("Iteration " + this.iteration);
            }

            progressor.progress(generator.next());

            ProgressionStatus status = progressor.getStatus();
//            addStatus(status);
//            addProperties(progressor.getProperties());
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

//    public synchronized List<ProgressionStatus> getStatus() {
//        List<ProgressionStatus> result = new LinkedList<>();
//        result.addAll(this.statusList);
//        return result;
//    }
//
//    private synchronized void addStatus(ProgressionStatus status) {
//        this.statusList.add(status);
//    }
//
//    public synchronized List<ProgressorProperties> getProperties() {
//        List<ProgressorProperties> result = new LinkedList<>();
//        result.addAll(this.propertiesList);
//        return result;
//    }
//
//    private synchronized void addProperties(ProgressorProperties properties) {
//        this.propertiesList.add(properties);
//    }
}
