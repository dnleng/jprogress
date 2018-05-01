package se.liu.ida.jprogress.formula;

/**
 * Created by dnleng on 30/04/18.
 */
public class FormulaFactory {

    private FormulaFactory() {}

    public static Formula createFormula(FormulaTemplate template) {
        switch(template) {
            case APEQ:
                return createAPEQ();
            case BERNOULLI:
                return createBernoulli();
            default:
                throw new IllegalArgumentException("Unknown template");
        }
    }

    private static Formula createAPEQ() {
        Atom p = new Atom("p");
        Atom q = new Atom("q");
        return new Always( new Disjunction( new Negation(p), new Eventually(0, 5, q) ) );
    }

    private static Formula createBernoulli() {
        return new Eventually( new Atom("p") );
    }
}
