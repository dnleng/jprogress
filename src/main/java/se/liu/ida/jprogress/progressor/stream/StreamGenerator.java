package se.liu.ida.jprogress.progressor.stream;

import se.liu.ida.jprogress.Interpretation;

/**
 * Created by Squig on 06/05/2018.
 */
public interface StreamGenerator {
    Interpretation next();
    boolean hasNext();
    void reset();
}
