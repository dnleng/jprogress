package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.Formula;

/**
 * Created by dnleng on 01/05/2018.
 */
public interface Progressor {
    void progress(Interpretation interpretation);

    void set(Formula input);

    ProgressionStatus getStatus();

    ProgressorProperties getProperties();
}
