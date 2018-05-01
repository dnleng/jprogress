package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.Formula;

import java.util.HashMap;

/**
 * Created by Squig on 01/05/2018.
 */
public class FormulaHistogram extends HashMap<String, Integer> {

    public FormulaHistogram() {
        super();
    }

    public int add(String f) {
        if(this.get(f) == null) {
            this.put(f, 1);
	}
	else {
            this.put(f, this.get(f)+1);
	}

	return this.get(f);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String key : this.keySet()) {
	    sb.append(this.get(key));
	    sb.append("\t\t:\t");
	    sb.append(key);
	    sb.append("\n");
	}

	return sb.toString();
    }
}
