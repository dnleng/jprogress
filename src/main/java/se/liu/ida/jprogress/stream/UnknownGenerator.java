package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;
import se.liu.ida.jprogress.reasoning.DefaultTheory;
import se.liu.ida.jprogress.reasoning.IClosure;

import java.util.List;
import java.util.Set;

/**
 * Created by dnleng on 06/05/2018.
 */
public class UnknownGenerator implements StreamGenerator {

    private Set<String> props;
    private IClosure theory;

    public UnknownGenerator(Set<String> props) {
        this.props = props;
        this.theory = new DefaultTheory();
    }

    public UnknownGenerator(Set<String> props, IClosure theory) {
        this.props = props;
        this.theory = theory;
    }

    @Override
    public Interpretation next() {
        Interpretation next = Interpretation.buildFullyUnknown(this.props);
        next.setClosureStrategy(this.theory);
        return next;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public void reset() {

    }
}
