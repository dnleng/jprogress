package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.progressor.graph.ProgressionGraph;

/**
 * Created by dnleng on 06/05/2018.
 */
public class OfflineProgressor extends ProgressionGraph {
    public OfflineProgressor(Formula formula) {
        super(ProgressionStrategy.OFFLINE, formula);
    }
}
