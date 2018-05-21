package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;

/**
 * Created by dnleng on 06/05/2018.
 */
public interface StreamGenerator {
    Interpretation next();

    boolean hasNext();

    void reset();
}
