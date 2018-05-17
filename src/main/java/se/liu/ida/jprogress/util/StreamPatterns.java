package se.liu.ida.jprogress.util;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.stream.FaultyRepeatingGenerator;
import se.liu.ida.jprogress.stream.RepeatingGenerator;
import se.liu.ida.jprogress.stream.StreamGenerator;

import java.util.*;

/**
 * Created by Squig on 06/05/2018.
 */
public class StreamPatterns {

    private StreamPatterns() {
    }

    public static StreamGenerator createAlteratingTrueFalse(String prop, int numTrue, int numFalse, int maxRepeats, double faultRatio) {
        List<Interpretation> pattern = new ArrayList<>(numTrue + numFalse);
        for (int i = 0; i < numTrue + numFalse; i++) {
            Interpretation interpretation = new Interpretation();
            if (i < numTrue) {
                interpretation.setTruthValue(prop, TruthValue.TRUE);
                pattern.add(interpretation);
            } else {
                interpretation.setTruthValue(prop, TruthValue.FALSE);
                pattern.add(interpretation);
            }
        }
        return new FaultyRepeatingGenerator(pattern, maxRepeats, faultRatio);
    }

    public static StreamGenerator createAlteratingFalseTrue(String prop, int numFalse, int numTrue, int maxRepeats, double faultRatio) {
        List<Interpretation> pattern = new ArrayList<>(numTrue + numFalse);
        for (int i = 0; i < numTrue + numFalse; i++) {
            Interpretation interpretation = new Interpretation();
            if (i < numFalse) {
                interpretation.setTruthValue(prop, TruthValue.FALSE);
                pattern.add(interpretation);
            } else {
                interpretation.setTruthValue(prop, TruthValue.TRUE);
                pattern.add(interpretation);
            }
        }
        return new FaultyRepeatingGenerator(pattern, maxRepeats, faultRatio);
    }

    public static StreamGenerator createConstant(String prop, boolean defaultTruth, int maxRepeats, double faultRatio, long seed) {
        Interpretation interpretation = new Interpretation();
        interpretation.setTruthValue(prop, defaultTruth ? TruthValue.TRUE : TruthValue.FALSE);
        List<Interpretation> list = new LinkedList<>();
        list.add(interpretation);
        return new FaultyRepeatingGenerator(list, maxRepeats, faultRatio, seed);
    }

    public static StreamGenerator createConstant(Map<String, Boolean> input, int maxRepeats, double faultRatio, long seed) {
        List<Interpretation> list = new LinkedList<>();
        Interpretation interpretation = new Interpretation();
        for(String prop : input.keySet()) {
            interpretation.setTruthValue(prop, input.get(prop) ? TruthValue.TRUE : TruthValue.FALSE);
        }
        list.add(interpretation);
        return new FaultyRepeatingGenerator(list, maxRepeats, faultRatio, seed);
    }

}
