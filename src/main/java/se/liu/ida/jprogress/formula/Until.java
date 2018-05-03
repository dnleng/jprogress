package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Until extends Formula {
    protected int startTime;
    protected int endTime;
    protected Formula lhs;
    protected Formula rhs;

    public Until(int startTime, int endTime, Formula lhs, Formula rhs) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Until(Formula lhs, Formula rhs) {
        super();
        this.startTime = 0;
        this.endTime = Integer.MAX_VALUE;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Formula progress(Interpretation interpretation) {
        if (this.endTime < 0) {
            return new Bottom();
        } else if (this.startTime <= 0 && 0 <= this.endTime) {
            return new Disjunction(this.rhs.progress(interpretation), new Conjunction(this.lhs.progress(interpretation), new Until(this.startTime == 0 ? 0 : this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.lhs, this.rhs)));
        } else {
            return new Conjunction(this.lhs.progress(interpretation), new Until(this.startTime == 0 ? 0 : this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.lhs, this.rhs));
        }
    }

    public TruthValue eval(Interpretation interpretation) {
        if (this.endTime < 0) {
            return TruthValue.FALSE;
        } else if (this.endTime == 0) {
            return this.rhs.eval(interpretation);
        } else {
            return TruthValue.UNKNOWN;
        }
    }

    @Override
    public Formula simplify(Interpretation interpretation) {
        if (this.eval(interpretation) == TruthValue.TRUE) {
            return new Top();
        } else if (this.eval(interpretation) == TruthValue.FALSE) {
            return new Bottom();
        }

        return this;
    }

    public Set<String> getAtoms() {
        Set<String> result = new HashSet<>();
        result.addAll(lhs.getAtoms());
        result.addAll(rhs.getAtoms());
        return result;
    }

    @Override
    public String toString() {
        if (this.startTime == 0 && this.endTime == Integer.MAX_VALUE) {
            return "(" + this.lhs + ") Until (" + this.rhs + ")";
        } else {
            return "(" + this.lhs + ") Until [" + this.startTime + " ; " + this.endTime + "] (" + this.rhs + ")";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Until until = (Until) o;

        if (startTime != until.startTime) return false;
        if (endTime != until.endTime) return false;
        if (!lhs.equals(until.lhs)) return false;
        return rhs.equals(until.rhs);
    }

    @Override
    public int hashCode() {
        int result = startTime;
        result = 31 * result + endTime;
        result = 31 * result + lhs.hashCode();
        result = 31 * result + rhs.hashCode();
        return result;
    }
}
