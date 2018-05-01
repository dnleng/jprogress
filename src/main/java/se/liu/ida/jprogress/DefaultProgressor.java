package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.Formula;

/**
 * Created by Squig on 01/05/2018.
 */
public class DefaultProgressor implements Progressor {

    private Formula input;

    public DefaultProgressor() {}

    public DefaultProgressor(Formula input) {
        this.input = input;
    }

    @Override public void progress(final Interpretation interpretation) {
        if(this.input != null) {
	    this.input = this.input.progress(interpretation);
	}
    }

    @Override public void set(final Formula input) {
	this.input = input;
    }

    public Formula get() {
        return this.input;
    }

    @Override
    public String toString() {
        return this.get().toString();
    }
}
