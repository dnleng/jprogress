package se.liu.ida.jprogress.util;

import se.liu.ida.jprogress.formula.Always;
import se.liu.ida.jprogress.formula.Atom;
import se.liu.ida.jprogress.formula.Disjunction;
import se.liu.ida.jprogress.formula.Eventually;
import se.liu.ida.jprogress.formula.Formula;
import se.liu.ida.jprogress.formula.Negation;

/**
 * Created by dnleng on 30/04/18.
 */
public class FormulaFactory {

    private FormulaFactory() {
    }

    public static Formula createFormula(FormulaTemplate template) {
        switch (template) {
            case APEQ:
                return createAPEQ(Integer.MAX_VALUE, 5);
            case BERNOULLI:
                return createBernoulli();
            case BIG_APEQ:
                return createAPEQ(1000, 400);
            default:
                throw new IllegalArgumentException("Unknown template");
        }
    }

    public static Formula implies(Formula p, Formula q) {
        return new Disjunction(new Negation(p), q);
    }

    private static Formula createAPEQ(int outer, int inner) {
        Atom p = new Atom("p");
        Atom q = new Atom("q");
        return new Always(outer, new Disjunction(new Negation(p), new Eventually(inner, q)));
    }

    private static Formula createBernoulli() {
        return new Eventually(new Atom("p"));
    }
}
