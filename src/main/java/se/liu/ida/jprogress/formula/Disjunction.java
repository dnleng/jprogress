package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Disjunction extends Formula {

    private Formula lhs;
    private Formula rhs;

    public Disjunction(Formula lhs, Formula rhs) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Formula progress(Interpretation interpretation) {
        return new Disjunction(lhs.progress(interpretation), rhs.progress(interpretation));
    }

    public TruthValue eval(Interpretation interpretation) {
        if (lhs.eval(interpretation) == TruthValue.TRUE || rhs.eval(interpretation) == TruthValue.TRUE) {
            return TruthValue.TRUE;
        } else if (lhs.eval(interpretation) == TruthValue.FALSE && rhs.eval(interpretation) == TruthValue.FALSE) {
            return TruthValue.FALSE;
        } else {
            return TruthValue.UNKNOWN;
        }
    }

    public void subsumption(Disjunction prevDisjunction, Formula prevTemporalOp, int prevDir, int thisDir) {
        Formula f;
        if(thisDir < 0) {
            f = this.lhs;
        }
        else {
            f = this.rhs;
        }

        if(prevTemporalOp instanceof Always && f instanceof Always) {
            if(((Always)prevTemporalOp).startTime == ((Always)f).startTime) {
                if(((Always)prevTemporalOp).endTime < ((Always)f).endTime) {
                    if(thisDir < 0) {
                        this.lhs = new Top();
                    }
                    else {
                        this.rhs = new Top();
                    }
                }
                else {
                    if(prevDir < 0) {
                        prevDisjunction.lhs = new Top();
                    }
                    else {
                        prevDisjunction.rhs = new Top();
                    }
                }
            }
        }
        else if(prevTemporalOp instanceof Eventually && f instanceof Eventually) {
            if(((Eventually)prevTemporalOp).startTime == ((Eventually)f).startTime) {
                if(((Eventually)prevTemporalOp).endTime < ((Eventually)f).endTime) {
                    if(prevDir < 0) {
                        prevDisjunction.lhs = new Top();
                    }
                    else {
                        prevDisjunction.rhs = new Top();
                    }
                }
                else {
                    if(thisDir < 0) {
                        this.lhs = new Top();
                    }
                    else {
                        this.rhs = new Top();
                    }
                }
            }
        }
        else if(prevTemporalOp instanceof Until && f instanceof Until) {
            if(((Until)prevTemporalOp).startTime == ((Until)f).startTime) {
                if(((Until)prevTemporalOp).endTime < ((Until)f).endTime) {
                    if(prevDir < 0) {
                        prevDisjunction.lhs = new Top();
                    }
                    else {
                        prevDisjunction.rhs = new Top();
                    }
                }
                else {
                    if(thisDir < 0) {
                        this.lhs = new Top();
                    }
                    else {
                        this.rhs = new Top();
                    }
                }
            }
        }
    }

    @Override
    public Formula subsumption(Interpretation interpretation) {
        if(this.lhs instanceof Disjunction) {
            if(this.rhs instanceof Always || this.rhs instanceof Eventually || this.rhs instanceof Until) {
                ((Disjunction)this.lhs).subsumption(this, this.rhs, 1, -1);
                ((Disjunction)this.lhs).subsumption(this, this.rhs, 1, 1);
            }
        }

        if(this.rhs instanceof Disjunction) {
            if(this.lhs instanceof Always || this.lhs instanceof Eventually || this.lhs instanceof Until) {
                ((Disjunction)this.rhs).subsumption(this, this.lhs, -1, -1);
                ((Disjunction)this.rhs).subsumption(this, this.lhs, -1, 1);
            }
        }

        this.lhs.subsumption(interpretation);
        this.rhs.subsumption(interpretation);
        return this.simplify(interpretation);
    }

    @Override
    public Formula simplify(Interpretation interpretation) {
        this.lhs = this.lhs.simplify(interpretation);
        this.rhs = this.rhs.simplify(interpretation);

        if (this.eval(interpretation) == TruthValue.FALSE) {
            return new Bottom();
        } else if (this.eval(interpretation) == TruthValue.TRUE) {
            return new Top();
        } else if (lhs.eval(interpretation) == TruthValue.FALSE && rhs.eval(interpretation) == TruthValue.UNKNOWN) {
            return rhs;
        } else if (lhs.eval(interpretation) == TruthValue.UNKNOWN && rhs.eval(interpretation) == TruthValue.FALSE) {
            return lhs;
        } else if(lhs.equals(rhs)) {
            return lhs;
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
        return "(" + this.lhs + ") ∨ (" + this.rhs + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disjunction that = (Disjunction) o;

        if (!lhs.equals(that.lhs)) return false;
        return rhs.equals(that.rhs);
    }

    @Override
    public int hashCode() {
        int result = lhs.hashCode();
        result = 31 * result + rhs.hashCode();
        return result;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Disjunction that = (Disjunction) o;
//
//        return ((lhs.equals(that.lhs) && rhs.equals(that.rhs)) || (lhs.equals(that.rhs) && rhs.equals(that.lhs)));
//    }
//
//    @Override
//    public int hashCode() {
//        int result = lhs.hashCode();
//        result = 31 * result + rhs.hashCode();
//        return result;
//    }
}
