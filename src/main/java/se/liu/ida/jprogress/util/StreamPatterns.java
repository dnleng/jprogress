package se.liu.ida.jprogress.util;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.formula.TruthValue;
import se.liu.ida.jprogress.stream.FaultyRepeatingGenerator;
import se.liu.ida.jprogress.stream.RepeatingGenerator;
import se.liu.ida.jprogress.stream.StreamGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    public static StreamGenerator createConstant(String prop, boolean defaultTruth, int maxRepeats, double faultRatio) {
        Interpretation interpretation = new Interpretation();
        interpretation.setTruthValue(prop, defaultTruth ? TruthValue.TRUE : TruthValue.FALSE);
        List<Interpretation> list = new LinkedList<>();
        list.add(interpretation);
        return new FaultyRepeatingGenerator(list, maxRepeats, faultRatio);
    }

}
