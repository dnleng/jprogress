package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by Squig on 06/05/2018.
 */
public class RepeatingGenerator implements StreamGenerator {

    protected List<Interpretation> interpretations;
    protected int index;

    public RepeatingGenerator(List<Interpretation> interpretations) {
	this.interpretations = interpretations;
	this.index = 0;
    }

    @Override public Interpretation next() {
        Interpretation result = interpretations.get(index);
        index = (index + 1) % interpretations.size();
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
