package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;
import java.util.Random;

/**
 * Created by Squig on 06/05/2018.
 */
public class FaultyRepeatingGenerator extends RepeatingGenerator {

    protected double faultProbability;
    protected Random rnd;

    public FaultyRepeatingGenerator(List<Interpretation> interpretations, int maxRepeats, double faultProbability, long seed) {
	super(interpretations, maxRepeats);
	this.faultProbability = faultProbability;
	this.rnd = new Random(seed);
    }

    public FaultyRepeatingGenerator(List<Interpretation> interpretations, int maxRepeats, double faultProbability) {
        super(interpretations, maxRepeats);
        this.faultProbability = faultProbability;
        this.rnd = new Random(System.currentTimeMillis());
    }

    @Override public Interpretation next() {
        Interpretation i = super.next();

        if(rnd.nextDouble() > faultProbability) {
            return i;
	}
	else {
            return Interpretation.buildFullyUnknown(i.getAtoms());
	}
    }
}
