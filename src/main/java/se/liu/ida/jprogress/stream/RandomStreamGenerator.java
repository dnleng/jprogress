package se.liu.ida.jprogress.stream;

/**
 * Created by Squig on 06/05/2018.
 */
public abstract class RandomStreamGenerator implements StreamGenerator {

    protected long seed;

    public RandomStreamGenerator() {
        this.seed = System.currentTimeMillis();
    }

    public RandomStreamGenerator(long seed) {
        this.seed = seed;
    }

}
