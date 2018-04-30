package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

/**
 * Created by dnleng on 30/04/18.
 */
public class Always extends Until {

    public Always(int startTime, int endTime, Formula formula) {
        super(startTime, endTime, formula, new Bottom());
    }

    public Always(Formula formula) {
        super(formula, new Bottom());
    }

    @Override
    public Formula progress(Interpretation interpretation) {
        if(this.endTime < 0) {
            return new Top();
        }
        else if(this.startTime <= 0 && 0 <= this.endTime) {
            return new Conjunction(this.lhs.progress(interpretation), new Always(this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.lhs));
        }
        else {
            return new Always(this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.lhs);
        }
    }

    @Override
    public String toString() {
        if(this.startTime == 0 && this.endTime == Integer.MAX_VALUE) {
            return "Always (" + this.lhs + ")";
        }
        else {
            return "Always [" + this.startTime + " ; " + this.endTime + "] (" + this.lhs + ")";
        }
    }
}
