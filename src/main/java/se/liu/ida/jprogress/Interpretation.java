package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.TruthValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dnleng on 30/04/18.
 */
public class Interpretation {

    private Map<String, TruthValue> truthFunc;

    public Interpretation() {
        this.truthFunc = new HashMap<>();
    }

    public static Interpretation buildFullyUnknown(List<String> props) {
        Interpretation result = new Interpretation();
        for (String prop : props) {
            result.setTruthValue(prop, TruthValue.UNKNOWN);
        }
        return result;
    }

    public TruthValue getTruthValue(String label) {
        return this.truthFunc.getOrDefault(label, TruthValue.UNKNOWN);
    }

    public void setTruthValue(String label, TruthValue value) {
        this.truthFunc.put(label, value);
    }

    public Set<Interpretation> getReductions() {
        List<String> trueProps = new LinkedList<>();
        List<String> falseProps = new LinkedList<>();
        List<String> unknownProps = new LinkedList<>();

        Set<Interpretation> result = new HashSet<>((int) Math.floor(Math.pow(2, unknownProps.size())));

        for (String key : this.truthFunc.keySet()) {
            switch (this.truthFunc.get(key)) {
                case TRUE:
                    trueProps.add(key);
                    break;
                case FALSE:
                    falseProps.add(key);
                    break;
                default:
                    unknownProps.add(key);
            }
        }

        if (unknownProps.size() == 0) {
            result.add(this);
        } else {
            for (int mask = 0; mask < (int) Math.floor(Math.pow(2, unknownProps.size())); mask++) {
                int bit = 1;
                Interpretation interpretation = new Interpretation();
                for (String trueProp : trueProps) {
                    interpretation.setTruthValue(trueProp, TruthValue.TRUE);
                }

                for (String falseProp : falseProps) {
                    interpretation.setTruthValue(falseProp, TruthValue.FALSE);
                }

                for (String s : unknownProps) {
                    if ((mask & bit) == bit) {
                        interpretation.setTruthValue(s, TruthValue.TRUE);
                    } else {
                        interpretation.setTruthValue(s, TruthValue.FALSE);
                    }

                    result.add(interpretation);
                    bit *= 2;
                }
            }
        }


        return result;
    }

    public int compress() {
        LinkedList<String> atoms = new LinkedList<>(this.truthFunc.keySet());
        Collections.sort(atoms);
        int result = 0;
        for (int i = 0; i < atoms.size(); i++) {
            if (this.truthFunc.get(atoms.get(i)) == TruthValue.TRUE) {
                result += ((int) Math.pow(2.0, i));
            } else if (this.truthFunc.get(atoms.get(i)) == TruthValue.UNKNOWN) {
                System.err.println("Warning: Proposition " + atoms.get(i) + " has unknown truth value; applying NAF");
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (String key : this.truthFunc.keySet()) {
            sb.append(key);
            sb.append(" => ");
            sb.append(truthFunc.get(key));
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }
}
