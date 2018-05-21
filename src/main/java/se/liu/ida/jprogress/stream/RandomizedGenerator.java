package se.liu.ida.jprogress.stream;

import java.util.List;
import java.util.Random;

/**
 * Created by dnleng on 06/05/2018.
 */
public abstract class RandomizedGenerator implements StreamGenerator {

    protected Random rnd;
    protected List<String> props;

    public RandomizedGenerator(List<String> props) {
        this.props = props;
        this.rnd = new Random(System.currentTimeMillis());
    }

    public RandomizedGenerator(List<String> props, long seed) {
        this.props = props;
        this.rnd = new Random(seed);
    }

}
