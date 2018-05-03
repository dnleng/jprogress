package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Always extends Formula {
    protected int startTime;
    protected int endTime;
    protected Formula formula;

    public Always(int startTime, int endTime, Formula formula) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.formula = formula;
    }

    public Always(Formula formula) {
        this.startTime = 0;
        this.endTime = Integer.MAX_VALUE;
        this.formula = formula;
    }

    @Override
    public Formula progress(Interpretation interpretation) {
        if (this.endTime == 0) {
            return new Top();
        } else if (this.startTime <= 0 && 0 <= this.endTime) {
            return new Conjunction(this.formula.progress(interpretation), new Always(this.startTime == 0 ? 0 : this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.formula));
        } else {
            return new Always(this.startTime == 0 ? 0 : this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.formula);
        }
    }

    @Override
    public TruthValue eval(Interpretation interpretation) {
        if (this.endTime < 0) {
            return TruthValue.FALSE;
        } else if (this.endTime == 0) {
            return this.formula.eval(interpretation);
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

    @Override
    public Set<String> getAtoms() {
        return this.formula.getAtoms();
    }

    @Override
    public String toString() {
        if (this.startTime == 0 && this.endTime == Integer.MAX_VALUE) {
            return "Always (" + this.formula + ")";
        } else {
            return "Always [" + this.startTime + " ; " + this.endTime + "] (" + this.formula + ")";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Always always = (Always) o;

        if (startTime != always.startTime) return false;
        if (endTime != always.endTime) return false;
        return formula.equals(always.formula);
    }

    @Override
    public int hashCode() {
        int result = startTime;
        result = 31 * result + endTime;
        result = 31 * result + formula.hashCode();
        return result;
    }
}
