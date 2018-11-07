// Generated from MTL.g4 by ANTLR 4.5.1

package se.liu.ida.jprogress.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MTLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MTLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MTLParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(MTLParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(MTLParser.FormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#imply_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImply_formula(MTLParser.Imply_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#or_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_formula(MTLParser.Or_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#and_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_formula(MTLParser.And_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#until_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUntil_formula(MTLParser.Until_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#eventually_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventually_formula(MTLParser.Eventually_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#always_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlways_formula(MTLParser.Always_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#not_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot_formula(MTLParser.Not_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#atom_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom_formula(MTLParser.Atom_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MTLParser#symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(MTLParser.SymbolContext ctx);
}