package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.reasoning.DefaultTheory;
import se.liu.ida.jprogress.reasoning.IClosure;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dnleng on 08/05/18.
 */
public class ComplexGenerator implements StreamGenerator {

    private List<StreamGenerator> generators;
    private IClosure theory;

    public ComplexGenerator() {
        this.generators = new LinkedList<>();
        this.theory = new DefaultTheory();
    }

    public ComplexGenerator(IClosure theory) {
        this.generators = new LinkedList<>();
        this.theory = theory;
    }

    public ComplexGenerator(List<StreamGenerator> generators) {
        this.generators = generators;
    }

    public void add(StreamGenerator generator) {
        this.generators.add(generator);
    }


    @Override
    public Interpretation next() {
        Interpretation result = new Interpretation();
        for(StreamGenerator generator : this.generators) {
            result.merge(generator.next());
        }
        result.setClosureStrategy(this.theory);

        return result;
    }

    @Override
    public boolean hasNext() {
        for(StreamGenerator generator : this.generators) {
            if(!generator.hasNext()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void reset() {
        for(StreamGenerator generator : this.generators) {
            generator.reset();
        }
    }
}
