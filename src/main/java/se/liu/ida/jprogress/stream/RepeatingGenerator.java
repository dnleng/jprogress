package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by Squig on 06/05/2018.
 */
public class RepeatingGenerator implements StreamGenerator {

    protected List<Interpretation> interpretations;
    protected int index;
    protected int maxRepeats;

    public RepeatingGenerator(List<Interpretation> interpretations) {
	this.interpretations = interpretations;
	this.index = 0;
	this.maxRepeats = Integer.MAX_VALUE;
    }

    public RepeatingGenerator(List<Interpretation> interpretations, int maxRepeats) {
    	this.interpretations = interpretations;
    	this.index = 0;
    	this.maxRepeats = maxRepeats;
    }

    @Override public Interpretation next() {
        Interpretation result = interpretations.get(index);
        index++;
        if(index == interpretations.size()) {
            index = index % interpretations.size();
            maxRepeats--;
            if(maxRepeats < 0) {
                reset();
	    }
	}
	return result;
    }

    @Override public boolean hasNext() {
	return !interpretations.isEmpty();
    }

    @Override public void reset() {
	this.interpretations.clear();
	this.index = 0;
    }
}
