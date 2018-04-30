package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by dnleng on 30/04/18.
 */
public abstract class Formula {
    public abstract Formula progress(Interpretation interpretation);
    public abstract TruthValue eval(Interpretation interpretation);
}
