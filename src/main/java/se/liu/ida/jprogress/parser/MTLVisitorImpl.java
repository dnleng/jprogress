package se.liu.ida.jprogress.parser;

import org.antlr.v4.runtime.tree.*;
import se.liu.ida.jprogress.formula.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dnleng on 24/05/18.
 */
public class MTLVisitorImpl extends MTLBaseVisitor<Formula> {

    private static final List<String> SKIP_LIST = Arrays.asList("(", ")", "[", "]", ",");

    @Override
    public Formula visit(ParseTree tree) {
        if(tree.getChildCount() == 2 && tree.getChild(1).getText().equals("<EOF>")) {
            return tree.getChild(0).accept(this);
        }
        else if(tree.getChildCount() == 1 && tree.getChild(0).getText().equals("<EOF>")) {
            return Top.getInstance();
        }
        else {
            System.err.println("Unexpected child list for " + tree.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitFormula(MTLParser.FormulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 1) {
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitAlways_formula(MTLParser.Always_formulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 2 && subtrees.get(0).getText().equals("always")) {
            return new Always(subtrees.get(1).accept(this));
        }
        else if(subtrees.size() == 4 && subtrees.get(0).getText().equals("always")) {
            int startTime = Integer.parseInt(subtrees.get(1).getText());
            int endTime = subtrees.get(2).getText().equals("oo") ? Integer.MAX_VALUE : Integer.parseInt(subtrees.get(2).getText());
            return new Always(startTime, endTime, subtrees.get(3).accept(this));
        }
        else if(subtrees.size() == 1) {
            // Fallthrough
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitEventually_formula(MTLParser.Eventually_formulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 2 && subtrees.get(0).getText().equals("eventually")) {
            return new Eventually(subtrees.get(1).accept(this));
        }
        else if(subtrees.size() == 4 && subtrees.get(0).getText().equals("eventually")) {
            int startTime = Integer.parseInt(subtrees.get(1).getText());
            int endTime = subtrees.get(2).getText().equals("oo") ? Integer.MAX_VALUE : Integer.parseInt(subtrees.get(2).getText());
            return new Eventually(startTime, endTime, subtrees.get(3).accept(this));
        }
        else if(subtrees.size() == 1) {
            // Fallthrough
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitUntil_formula(MTLParser.Until_formulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 3 && subtrees.get(1).getText().equals("until")) {
            return new Until(subtrees.get(0).accept(this), subtrees.get(2).accept(this));
        }
        else if(subtrees.size() == 5 && subtrees.get(1).getText().equals("until")) {
            int startTime = Integer.parseInt(subtrees.get(2).getText());
            int endTime = subtrees.get(3).getText().equals("oo") ? Integer.MAX_VALUE : Integer.parseInt(subtrees.get(3).getText());
            return new Until(startTime, endTime, subtrees.get(0).accept(this), subtrees.get(4).accept(this));
        }
        else if(subtrees.size() == 1) {
            // Fallthrough
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitNot_formula(MTLParser.Not_formulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 2 && subtrees.get(0).getText().equals("not")) {
            return new Negation(subtrees.get(1).accept(this));
        }
        else if(subtrees.size() == 1) {
            // Fallthrough
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitImply_formula(MTLParser.Imply_formulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 3 && subtrees.get(1).getText().equals("->")) {
            return new Disjunction(new Negation(subtrees.get(0).accept(this)), subtrees.get(2).accept(this));
        }
        else if(subtrees.size() == 1) {
            // Fallthrough
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitAnd_formula(MTLParser.And_formulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 3 && subtrees.get(1).getText().equals("and")) {
            return new Conjunction(subtrees.get(0).accept(this), subtrees.get(2).accept(this));
        }
        else if(subtrees.size() == 1) {
            // Fallthrough
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitOr_formula(MTLParser.Or_formulaContext ctx) {
        List<ParseTree> subtrees = new ArrayList<>();
        for(int i = 0; i < ctx.getChildCount() ; i++) {
            ParseTree child = ctx.getChild(i);
            if(!SKIP_LIST.contains(child.getText())) {
                subtrees.add(child);
            }
        }

        if(subtrees.size() == 3 && subtrees.get(1).getText().equals("or")) {
            return new Disjunction(subtrees.get(0).accept(this), subtrees.get(2).accept(this));
        }
        else if(subtrees.size() == 1) {
            // Fallthrough
            return subtrees.get(0).accept(this);
        }
        else {
            System.err.println("Unexpected child list for " + ctx.getText());
            return Bottom.getInstance();
        }
    }

    @Override
    public Formula visitAtom_formula(MTLParser.Atom_formulaContext ctx) {
        switch(ctx.getText()) {
            case "true":
                return Top.getInstance();
            case "false":
                return Bottom.getInstance();
            default:
                return new Atom(ctx.getText());
        }
    }
}
