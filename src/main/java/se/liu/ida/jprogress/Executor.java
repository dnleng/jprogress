package se.liu.ida.jprogress;

import se.liu.ida.jprogress.progressor.Progressor;
import se.liu.ida.jprogress.progressor.graph.ProgressionStatus;
import se.liu.ida.jprogress.progressor.stream.StreamGenerator;

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
    private List<ProgressionStatus> statusList;
    private int iteration;

    public Executor(Progressor progressor, StreamGenerator generator, double terminator) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = terminator;
        this.running = false;
        this.statusList = new LinkedList<>();
        this.iteration = 0;
    }

    public Executor(Progressor progressor, StreamGenerator generator) {
	this.progressor = progressor;
	this.generator = generator;
	this.terminator = 1.0;
	this.running = false;
	this.statusList = new LinkedList<>();
	this.iteration = 0;
    }


    @Override
    public void run() {
        this.running = true;
        while(generator.hasNext()) {
            progressor.progress(generator.next());

            ProgressionStatus status = progressor.getStatus();
	    addStatus(status);
	    if(status.getTrueVerdict() >= terminator || status.getFalseVerdict() >= terminator || status.getUnknownVerdict() >= terminator) {
	        break;
	    }
	    iteration++;
	}
	this.running = false;
    }

    public synchronized boolean isRunning() {
        return this.running;
    }

    public int getIteration() {
        return this.iteration;
    }

    public synchronized List<ProgressionStatus> getStatus() {
        List<ProgressionStatus> result = new LinkedList<>();
        result.addAll(this.statusList);
        return result;
    }

    private synchronized void addStatus(ProgressionStatus status) {
        this.statusList.add(status);
    }
}
