package se.liu.ida.jprogress.formula;

/**
 * Created by dnleng on 30/04/18.
 */
public class FormulaFactory {

    private FormulaFactory() {
    }

    public static Formula createFormula(FormulaTemplate template) {
        switch (template) {
            case APEQ:
                return createAPEQ(5);
            case BERNOULLI:
                return createBernoulli();
            case BIG_APEQ:
                return createAPEQ(14);
            default:
                throw new IllegalArgumentException("Unknown template");
        }
    }

    private static Formula createAPEQ(int end) {
        Atom p = new Atom("p");
        Atom q = new Atom("q");
        return new Always(new Disjunction(new Negation(p), new Eventually(0, end, q)));
    }

    private static Formula createBernoulli() {
        return new Eventually(new Atom("p"));
    }
}
