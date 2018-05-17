package se.liu.ida.jprogress.reasoning;

import se.liu.ida.jprogress.formula.TruthValue;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Squig on 17/05/2018.
 */
public class HornTheory implements IClosure {
    private List<Rule> ruleList;

    public HornTheory() {
        this.ruleList = new LinkedList<>();
    }

    public void addRule(Rule rule) {
        this.ruleList.add(rule);
    }

    @Override
    public boolean close(Map<String, TruthValue> truthFunc) {
        for(Rule rule : ruleList) {
            // Find a matching body
            boolean match = true;
            for(Literal lit : rule.body) {
                if(truthFunc.getOrDefault(lit.atom, TruthValue.UNKNOWN) != lit.truthValue) {
                    match = false;
                    break;
                }
            }

            // Check if the head holds
            if(match && truthFunc.getOrDefault(rule.head.atom, TruthValue.UNKNOWN) != rule.head.truthValue) {
                return false;
            }
        }

        return true;
    }
}
