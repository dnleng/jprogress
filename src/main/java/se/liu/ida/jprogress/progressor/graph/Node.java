package se.liu.ida.jprogress.progressor.graph;

import se.liu.ida.jprogress.formula.Formula;

import java.util.Set;

/**
 * Created by dnleng on 04/05/18.
 */
public class Node implements Comparable<Node> {
    public Formula formula;
    public Set<Transition> transitions;
    public double mass;
    public boolean expanded;
    public int age;

    public Node(Formula formula, Set<Transition> transitions, double mass, boolean expanded, int age) {
        this.formula = formula;
        this.transitions = transitions;
        this.mass = mass;
        this.expanded = expanded;
        this.age = age;
    }

    @Override
    public int compareTo(Node node) {
        if (this.mass < node.mass) {
            return 1;
        } else {
            return -1;
        }
    }
}
