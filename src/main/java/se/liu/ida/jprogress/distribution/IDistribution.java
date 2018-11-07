package se.liu.ida.jprogress.distribution;

import se.liu.ida.jprogress.Interpretation;

import java.util.Set;

/**
 * Created by dnleng on 20/06/18.
 */
public interface IDistribution {
    double getProbability(int interpretation);
    IDistribution rebuild(Set<String> atoms);
}
