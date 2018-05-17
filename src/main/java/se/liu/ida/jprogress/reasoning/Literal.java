package se.liu.ida.jprogress.reasoning;

import se.liu.ida.jprogress.formula.TruthValue;

/**
 * Created by Squig on 17/05/2018.
 */
public class Literal {

    public String atom;
    public TruthValue truthValue;


    public Literal(final String atom, final TruthValue truthValue) {
	this.atom = atom;
	this.truthValue = truthValue;
    }
}
