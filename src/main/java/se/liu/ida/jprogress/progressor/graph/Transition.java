package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.Interpretation;

import java.util.UUID;

/**
 * Created by Squig on 01/05/2018.
 */
public class Transition {
    public int interpretation;
    public Node destNode;

    public Transition(Interpretation interpretation, Node destNode) {
        this.interpretation = interpretation.compress();
        this.destNode = destNode;
    }
}
