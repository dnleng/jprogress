package se.liu.ida.jprogress.stream;

import se.liu.ida.jprogress.Interpretation;

import java.util.List;

/**
 * Created by Squig on 06/05/2018.
 */
public class UnknownGenerator implements StreamGenerator {

    private List<String> props;

    public UnknownGenerator(List<String> props) {
        this.props = props;
    }

    @Override
    public Interpretation next() {
        return Interpretation.buildFullyUnknown(this.props);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public void reset() {

    }
}
