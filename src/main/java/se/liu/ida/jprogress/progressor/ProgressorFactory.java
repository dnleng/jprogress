package se.liu.ida.jprogress.progressor;

import se.liu.ida.jprogress.formula.Formula;

/**
 * Created by Squig on 06/05/2018.
 */
public class ProgressorFactory {

    private int maxTTL;
    private int maxNodes;

    public ProgressorFactory() {
        this.maxTTL = Integer.MAX_VALUE;
        this.maxNodes = Integer.MAX_VALUE;
    }

    public ProgressorFactory(int maxTTL, int maxNodes) {
	this.maxTTL = maxTTL;
	this.maxNodes = maxNodes;
    }

    public Progressor create(Formula formula, ProgressionStrategy strategy) {
        switch(strategy) {
	    case OFFLINE:
	        return new OfflineProgressor(formula);
	    case ONLINE:
		return new OnlineProgressor(formula, this.maxTTL);
	    case LEAKY:
		return new LeakyProgressor(formula, this.maxTTL, this.maxNodes);
	    case NAIVE:
		return new NaiveProgressor(formula);
	    case DEFAULT:
	    default:
	        return new DefaultProgressor(formula);
	}
    }

    public int getMaxTTL() {
	return maxTTL;
    }

    public void setMaxTTL(final int maxTTL) {
	this.maxTTL = maxTTL;
    }

    public int getMaxNodes() {
	return maxNodes;
    }

    public void setMaxNodes(final int maxNodes) {
	this.maxNodes = maxNodes;
    }
}
