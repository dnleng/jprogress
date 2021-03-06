package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

/**
 * Created by dnleng on 30/04/18.
 */
public class Eventually extends Until {

    public Eventually(int startTime, int endTime, Formula formula) {
        super(startTime, endTime, Top.getInstance(), formula);
    }

    public Eventually(int endTime, Formula formula) {
        super(endTime, Top.getInstance(), formula);
    }

    public Eventually(Formula formula) {
        super(Top.getInstance(), formula);
    }

    @Override
    public Formula progress(Interpretation interpretation) {
        if (this.endTime < 0) {
            return Bottom.getInstance();
        } else if (this.startTime <= 0 && 0 <= this.endTime) {
            return new Disjunction(this.rhs.progress(interpretation), new Eventually(this.startTime == 0 ? 0 : this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.rhs));
        } else {
            return new Eventually(this.startTime == 0 ? 0 : this.startTime - 1, this.endTime == Integer.MAX_VALUE ? this.endTime : this.endTime - 1, this.rhs);
        }
    }

    @Override
    public String toString() {
        if (this.startTime == 0 && this.endTime == Integer.MAX_VALUE) {
            return "Eventually (" + this.rhs + ")";
        } else {
            return "Eventually [" + this.startTime + " ; " + this.endTime + "] (" + this.rhs + ")";
        }
    }
}
