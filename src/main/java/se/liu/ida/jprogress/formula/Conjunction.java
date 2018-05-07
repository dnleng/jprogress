package se.liu.ida.jprogress.formula;

import se.liu.ida.jprogress.Interpretation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Conjunction extends Formula {
    private Formula lhs;
    private Formula rhs;

    public Conjunction(Formula lhs, Formula rhs) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Formula progress(Interpretation interpretation) {
        return new Conjunction(lhs.progress(interpretation), rhs.progress(interpretation));
    }

    public TruthValue eval(Interpretation interpretation) {
        if (lhs.eval(interpretation) == TruthValue.TRUE && rhs.eval(interpretation) == TruthValue.TRUE) {
            return TruthValue.TRUE;
        } else if (lhs.eval(interpretation) == TruthValue.FALSE || rhs.eval(interpretation) == TruthValue.FALSE) {
            return TruthValue.FALSE;
        } else {
            return TruthValue.UNKNOWN;
        }
    }


    public void subsumption(Conjunction prevConjunction, Formula prevTemporalOp, int prevDir, int thisDir) {
        Formula f;
        if (thisDir < 0) {
            f = this.lhs;
        } else {
            f = this.rhs;
        }

        if (prevTemporalOp instanceof Always && f instanceof Always) {
            if (((Always) prevTemporalOp).formula.equals(((Always) f).formula)
                    && ((Always) prevTemporalOp).startTime == ((Always) f).startTime) {
                if (((Always) prevTemporalOp).endTime < ((Always) f).endTime) {
                    if (prevDir < 0) {
                        prevConjunction.lhs = Top.getInstance();
                    } else {
                        prevConjunction.rhs = Top.getInstance();
                    }
                } else {
                    if (thisDir < 0) {
                        this.lhs = Top.getInstance();
                    } else {
                        this.rhs = Top.getInstance();
                    }
                }
            }
        } else if (prevTemporalOp instanceof Until && f instanceof Until) {
            if (((Until) prevTemporalOp).lhs.equals(((Until) f).lhs)
                    && ((Until) prevTemporalOp).rhs.equals(((Until) f).rhs)
                    && ((Until) prevTemporalOp).startTime == ((Until) f).startTime) {
                if (((Until) prevTemporalOp).endTime < ((Until) f).endTime) {
                    if (thisDir < 0) {
                        this.lhs = Top.getInstance();
                    } else {
                        this.rhs = Top.getInstance();
                    }
                } else {
                    if (prevDir < 0) {
                        prevConjunction.lhs = Top.getInstance();
                    } else {
                        prevConjunction.rhs = Top.getInstance();
                    }
                }
            }
        }
    }

    @Override
    public Formula subsumption() {
        if (this.lhs instanceof Always && this.rhs instanceof Always
                && ((Always) this.lhs).formula.equals(((Always) this.rhs).formula)
                && ((Always) this.lhs).startTime == ((Always) this.rhs).startTime) {
            if (((Always) this.lhs).endTime > ((Always) this.rhs).endTime) {
                this.rhs = Top.getInstance();
            } else {
                this.lhs = Top.getInstance();
            }
        } else if (this.lhs instanceof Until && this.rhs instanceof Until
                && ((Until) this.lhs).lhs.equals(((Until) this.rhs).lhs)
                && ((Until) this.lhs).rhs.equals(((Until) this.rhs).rhs)
                && ((Until) this.lhs).startTime == ((Until) this.rhs).startTime) {
            if (((Until) this.lhs).endTime > ((Until) this.rhs).endTime) {
                this.lhs = Top.getInstance();
            } else {
                this.rhs = Top.getInstance();
            }
        }

        if (this.lhs instanceof Conjunction) {
            if (this.rhs instanceof Always || this.rhs instanceof Eventually || this.rhs instanceof Until) {
                ((Conjunction) this.lhs).subsumption(this, this.rhs, 1, -1);
                ((Conjunction) this.lhs).subsumption(this, this.rhs, 1, 1);
            }
        }

        if (this.rhs instanceof Conjunction) {
            if (this.lhs instanceof Always || this.lhs instanceof Eventually || this.lhs instanceof Until) {
                ((Conjunction) this.rhs).subsumption(this, this.lhs, -1, -1);
                ((Conjunction) this.rhs).subsumption(this, this.lhs, -1, 1);
            }
        }

        this.lhs.subsumption();
        this.rhs.subsumption();
        return this.simplify();
    }

    @Override
    public Formula simplify() {
        this.lhs = this.lhs.simplify();
        this.rhs = this.rhs.simplify();

        if (this.lhs.equals(this.rhs)) {
            return lhs;
        } else if (this.lhs instanceof Bottom) {
            return Bottom.getInstance();
        } else if (this.rhs instanceof Bottom) {
            return Bottom.getInstance();
        } else if (this.lhs instanceof Top) {
            return this.rhs;
        } else if (this.rhs instanceof Top) {
            return this.lhs;
        }

        return this;
    }

    @Override
    public int getSize() {
        return 1 + lhs.getSize() + rhs.getSize();
    }

    public Set<String> getAtoms() {
        Set<String> result = new HashSet<>();
        result.addAll(lhs.getAtoms());
        result.addAll(rhs.getAtoms());
        return result;
    }

    @Override
    public String toString() {
        return "(" + this.lhs + ") ∧ (" + this.rhs + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conjunction that = (Conjunction) o;

        return ((lhs.equals(that.lhs) && rhs.equals(that.rhs)) || (lhs.equals(that.rhs) && rhs.equals(that.lhs)));
    }

    @Override
    public int hashCode() {
        int result = lhs.hashCode();
        result = 31 * result + rhs.hashCode();
        return result;
    }
}
