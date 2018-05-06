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
    private List<ProgressionStatus> statusList;
    private List<ProgressorProperties> propertiesList;

    public Executor(Progressor progressor, StreamGenerator generator, double terminator) {
        this.progressor = progressor;
        this.generator = generator;
        this.terminator = terminator;
        this.running = false;
        this.statusList = new LinkedList<>();
	this.propertiesList = new LinkedList<>();
    }

    public Executor(Progressor progressor, StreamGenerator generator) {
	this.progressor = progressor;
	this.generator = generator;
	this.terminator = 1.0;
	this.running = false;
	this.statusList = new LinkedList<>();
	this.propertiesList = new LinkedList<>();
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
	}
	this.running = false;
    }

    public synchronized boolean isRunning() {
        return this.running;
    }

    public int getIteration() {
        return this.statusList.size();
    }

    public synchronized List<ProgressionStatus> getStatus() {
        List<ProgressionStatus> result = new LinkedList<>();
        result.addAll(this.statusList);
        return result;
    }

    private synchronized void addStatus(ProgressionStatus status) {
        this.statusList.add(status);
    }

    public synchronized List<ProgressorProperties> getProperties() {
	List<ProgressorProperties> result = new LinkedList<>();
	result.addAll(this.propertiesList);
	return result;
    }

    private synchronized void addProperties(ProgressorProperties properties) {
	this.propertiesList.add(properties);
    }
}
