package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.progressor.graph.ProgressionGraph;

/**
 * Created by Squig on 06/05/2018.
 */
public class OnlineProgressor extends ProgressionGraph {
    public OnlineProgressor(Formula formula, int maxTTL) {
        super(ProgressionStrategy.ONLINE, formula);
        this.setTTL(maxTTL);
    }
}
