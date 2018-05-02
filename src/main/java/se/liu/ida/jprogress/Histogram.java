package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.Formula;

import java.util.HashMap;

/**
 * Created by Squig on 01/05/2018.
 */
public class Histogram extends HashMap<String, Integer> {

    private int count;

    public Histogram() {
        super();
        this.count = 0;
    }

    public int add(String f) {
        if (this.get(f) == null) {
            this.put(f, 1);
        } else {
            this.put(f, this.get(f) + 1);
        }

        this.count++;
        return this.get(f);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : this.keySet()) {
            int i = this.get(key);
            double r = Math.floor(((double) i / (double) this.count) * 10000.0) / 10000.0;

            sb.append(i);
            sb.append("\t\t(");
            sb.append(r);
            sb.append("):\t");
            sb.append(key);
            sb.append("\n");
        }
        sb.append("--------\n");
        sb.append(this.count);
        sb.append("\n");

        return sb.toString();
    }
}
