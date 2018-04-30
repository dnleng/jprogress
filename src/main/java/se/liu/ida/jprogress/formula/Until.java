package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by dnleng on 30/04/18.
 */
public class Until extends Formula {
    protected int startTime;
    protected int endTime;
    protected Formula lhs;
    protected Formula rhs;

    public Until(int startTime, int endTime, Formula lhs, Formula rhs) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Until(Formula lhs, Formula rhs) {
        this.startTime = 0;
        this.endTime = Integer.MAX_VALUE;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Formula progress(Interpretation interpretation) {
        if(this.endTime < 0) {
            return new Bottom();
        }
        else if(this.startTime <= 0 && 0 <= this.endTime) {
            return new Disjunction(this.rhs.progress(interpretation), new Conjunction(this.lhs.progress(interpretation), new Until(this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.lhs, this.rhs)));
        }
        else {
            return new Conjunction(this.lhs.progress(interpretation), new Until(this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.lhs, this.rhs));
        }
    }

    public TruthValue eval(Interpretation interpretation) {
        return TruthValue.UNKNOWN;
    }

    @Override
    public String toString() {
        if(this.startTime == 0 && this.endTime == Integer.MAX_VALUE) {
            return "(" + this.lhs + ") Until (" + this.rhs + ")";
        }
        else {
            return "(" + this.lhs + ") Until [" + this.startTime + " ; " + this.endTime + "] (" + this.rhs + ")";
        }
    }
}
