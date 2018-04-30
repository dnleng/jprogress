package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.FormulaFactory;
import se.liu.ida.jprogress.formula.FormulaTemplate;
import se.liu.ida.jprogress.formula.TruthValue;

/**
 * Created by dnleng on 30/04/18.
 */
public class Main {
    public static void main(String[] args) {
        Interpretation interpretation = new Interpretation();
        interpretation.setTruthValue("p", TruthValue.TRUE);
        interpretation.setTruthValue("q", TruthValue.FALSE);

        System.out.println("Formula: " + FormulaFactory.createFormula(FormulaTemplate.APEQ));
        System.out.println("Progressed: " + FormulaFactory.createFormula(FormulaTemplate.APEQ).progress(interpretation));
        System.out.println("Evaluation: " + FormulaFactory.createFormula(FormulaTemplate.APEQ).progress(interpretation).eval(interpretation));
    }
}
