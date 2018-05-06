package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by Squig on 06/05/2018.
 */
public class DefaultGenerator implements StreamGenerator {

    protected List<Interpretation> interpretations;

    public DefaultGenerator(List<Interpretation> interpretations) {
        init(interpretations);
    }

    @Override public Interpretation next() {
	return interpretations.remove(0);
    }

    @Override public boolean hasNext() {
	return !interpretations.isEmpty();
    }

    @Override public void reset() {
	interpretations.clear();
    }

    public void init(List<Interpretation> interpretations) {
	this.interpretations = interpretations;
    }
}
