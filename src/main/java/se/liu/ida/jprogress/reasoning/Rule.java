package se.liu.ida.jprogress.reasoning;

import java.util.List;

/**
 * Created by Squig on 17/05/2018.
 */
public class Rule {
    public Literal head;
    public List<Literal> body;

    public Rule(final Literal head, final List<Literal> body) {
	this.head = head;
	this.body = body;
    }
}
