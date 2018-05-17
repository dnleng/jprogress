package se.liu.ida.jprogress;

import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.reasoning.DefaultTheory;
import se.liu.ida.jprogress.reasoning.IClosure;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by dnleng on 30/04/18.
 */
public class Interpretation {

    private Map<String, TruthValue> truthFunc;
    private IClosure closureStrategy;

    public Interpretation() {
        this.truthFunc = new HashMap<>();
        this.closureStrategy = new DefaultTheory();
    }

    public Interpretation(IClosure closureStrategy) {
        this.truthFunc = new HashMap<>();
        this.closureStrategy = closureStrategy;
    }

    public void merge(Interpretation interpretation) {
        for(String atom : interpretation.getAtoms()) {
            if(getTruthValue(atom) == TruthValue.UNKNOWN) {
                setTruthValue(atom, interpretation.getTruthValue(atom));
            }
            else if(getTruthValue(atom) != interpretation.getTruthValue(atom)) {
                throw new IllegalStateException("Attempting to merge inconsistent interpretations");
            }
        }
    }

    public boolean close() {
        return this.closureStrategy.close(this.truthFunc);
    }

    public static Interpretation buildFullyUnknown(Set<String> props) {
        Interpretation result = new Interpretation();
        for (String prop : props) {
            result.setTruthValue(prop, TruthValue.UNKNOWN);
        }
        return result;
    }

    public static Interpretation buildFullyUnknown(List<String> props) {
        Interpretation result = new Interpretation();
        for (String prop : props) {
            result.setTruthValue(prop, TruthValue.UNKNOWN);
        }
        return result;
    }

    public static Interpretation buildRandom(List<String> props) {
        Random rand = new Random();
        Interpretation result = new Interpretation();
        for (String prop : props) {
            switch (rand.nextInt(2)) {
                case 0:
                    result.setTruthValue(prop, TruthValue.TRUE);
                    break;
                case 1:
                    result.setTruthValue(prop, TruthValue.FALSE);
                    break;
                default:
                    result.setTruthValue(prop, TruthValue.UNKNOWN);
                    break;
            }
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
                Interpretation interpretation = new Interpretation(this.getClosureStrategy());
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

        // Filter based on background theory
        Predicate<Interpretation> predicate = i -> !i.close();
        result.removeIf(predicate);

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

    public List<String> getAtoms() {
        List<String> result = new ArrayList<>(this.truthFunc.keySet().size());
        result.addAll(this.truthFunc.keySet());
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

    public void setClosureStrategy(IClosure closureStrategy) {
        this.closureStrategy = closureStrategy;
    }

    public IClosure getClosureStrategy() {
        return closureStrategy;
    }
}
