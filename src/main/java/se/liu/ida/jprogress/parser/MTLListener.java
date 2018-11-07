// Generated from MTL.g4 by ANTLR 4.5.1

package se.liu.ida.jprogress.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MTLParser}.
 */
public interface MTLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MTLParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(MTLParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(MTLParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(MTLParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(MTLParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#imply_formula}.
	 * @param ctx the parse tree
	 */
	void enterImply_formula(MTLParser.Imply_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#imply_formula}.
	 * @param ctx the parse tree
	 */
	void exitImply_formula(MTLParser.Imply_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#or_formula}.
	 * @param ctx the parse tree
	 */
	void enterOr_formula(MTLParser.Or_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#or_formula}.
	 * @param ctx the parse tree
	 */
	void exitOr_formula(MTLParser.Or_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#and_formula}.
	 * @param ctx the parse tree
	 */
	void enterAnd_formula(MTLParser.And_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#and_formula}.
	 * @param ctx the parse tree
	 */
	void exitAnd_formula(MTLParser.And_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#until_formula}.
	 * @param ctx the parse tree
	 */
	void enterUntil_formula(MTLParser.Until_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#until_formula}.
	 * @param ctx the parse tree
	 */
	void exitUntil_formula(MTLParser.Until_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#eventually_formula}.
	 * @param ctx the parse tree
	 */
	void enterEventually_formula(MTLParser.Eventually_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#eventually_formula}.
	 * @param ctx the parse tree
	 */
	void exitEventually_formula(MTLParser.Eventually_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#always_formula}.
	 * @param ctx the parse tree
	 */
	void enterAlways_formula(MTLParser.Always_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#always_formula}.
	 * @param ctx the parse tree
	 */
	void exitAlways_formula(MTLParser.Always_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#not_formula}.
	 * @param ctx the parse tree
	 */
	void enterNot_formula(MTLParser.Not_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#not_formula}.
	 * @param ctx the parse tree
	 */
	void exitNot_formula(MTLParser.Not_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#atom_formula}.
	 * @param ctx the parse tree
	 */
	void enterAtom_formula(MTLParser.Atom_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#atom_formula}.
	 * @param ctx the parse tree
	 */
	void exitAtom_formula(MTLParser.Atom_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link MTLParser#symbol}.
	 * @param ctx the parse tree
	 */
	void enterSymbol(MTLParser.SymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link MTLParser#symbol}.
	 * @param ctx the parse tree
	 */
	void exitSymbol(MTLParser.SymbolContext ctx);
}