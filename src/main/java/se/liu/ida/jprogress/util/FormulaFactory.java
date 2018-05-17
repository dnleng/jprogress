package se.liu.ida.jprogress.util;

import se.liu.ida.jprogress.formula.*;

/**
 * Created by dnleng on 30/04/18.
 */
public class FormulaFactory {

    private FormulaFactory() {
    }

    public static Formula createFormula(FormulaTemplate template) {
        switch (template) {
            case APEQ:
                return createAPEQ(Integer.MAX_VALUE, 10);
            case BERNOULLI:
                return createBernoulli(Integer.MAX_VALUE);
            case BIG_APEQ:
                return createAPEQ(10000, 4000);
            default:
                throw new IllegalArgumentException("Unknown template");
        }
    }

    public static Formula implies(Formula p, Formula q) {
        return new Disjunction(new Negation(p), q);
    }

    public static Formula createAPEQ(int outer, int inner) {
        Atom p = new Atom("p");
        Atom q = new Atom("q");
        return new Always(outer, new Disjunction(new Negation(p), new Eventually(inner, q)));
    }

    public static Formula createBernoulli(int i) {
        return new Eventually(i, new Atom("p"));
    }

    public static Formula createAEP(int endTime) {
        return new Always(new Eventually(0, endTime, new Atom("p")));
    }

    public static Formula createAPEAI(int endTime) {
        return new Always(new Disjunction(new Atom("p"), new Eventually(0, endTime, new Always(0, endTime - 1, new Atom("p")))));
    }

    public static Formula createTypeA(int i1, int i2) {
        Atom p = new Atom("p");
        Atom q = new Atom("q");
        return new Always(i1, new Disjunction(new Negation(p), new Eventually(i2, q)));
    }

    public static Formula createTypeB(int i1, int i2) {
        Atom p = new Atom("p");
        return new Always(i1, new Disjunction(p, new Eventually(i2, p)));
    }

    public static Formula createTypeC(int i1, int i2, int i3) {
        Atom p = new Atom("p");
        return new Always(i1, new Disjunction(p, new Eventually(i2, new Always(i3, p))));
    }

    public static Formula createTypeD(int i1, int i2) {
        return new Conjunction(new Eventually(i1, new Disjunction(new Atom("p"), new Always(i2, new Atom("q")))), new Always(i1, new Atom("r")));
    }

    public static Formula createType2Chi(int i1, int i2, int i3) {
        return new Always(i1, new Disjunction(new Negation(new Atom("p")), new Eventually(i2, new Disjunction(new Negation(new Atom("q")), new Always(i3, new Atom("r"))))));
    }

}
