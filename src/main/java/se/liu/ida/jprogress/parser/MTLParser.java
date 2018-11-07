// Generated from MTL.g4 by ANTLR 4.5.1

package se.liu.ida.jprogress.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MTLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, INTEGER=2, COMMA=3, LPAREN=4, RPAREN=5, LBRACKET=6, RBRACKET=7, 
		NOT=8, AND=9, OR=10, IMPLY=11, ALWAYS=12, EVENTUALLY=13, UNTIL=14, CONST_TRUTH=15, 
		ID=16, WS=17;
	public static final int
		RULE_start = 0, RULE_formula = 1, RULE_imply_formula = 2, RULE_or_formula = 3, 
		RULE_and_formula = 4, RULE_until_formula = 5, RULE_eventually_formula = 6, 
		RULE_always_formula = 7, RULE_not_formula = 8, RULE_atom_formula = 9, 
		RULE_symbol = 10;
	public static final String[] ruleNames = {
		"start", "formula", "imply_formula", "or_formula", "and_formula", "until_formula", 
		"eventually_formula", "always_formula", "not_formula", "atom_formula", 
		"symbol"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'oo'", null, "','", "'('", "')'", "'['", "']'", "'not'", "'and'", 
		"'or'", "'->'", "'always'", "'eventually'", "'until'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "INTEGER", "COMMA", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", 
		"NOT", "AND", "OR", "IMPLY", "ALWAYS", "EVENTUALLY", "UNTIL", "CONST_TRUTH", 
		"ID", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MTL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MTLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MTLParser.EOF, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			setState(26);
			switch (_input.LA(1)) {
			case EOF:
				enterOuterAlt(_localctx, 1);
				{
				setState(22);
				match(EOF);
				}
				break;
			case LPAREN:
			case NOT:
			case ALWAYS:
			case EVENTUALLY:
			case CONST_TRUTH:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(23);
				formula();
				setState(24);
				match(EOF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormulaContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(MTLParser.LPAREN, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MTLParser.RPAREN, 0); }
		public Atom_formulaContext atom_formula() {
			return getRuleContext(Atom_formulaContext.class,0);
		}
		public Not_formulaContext not_formula() {
			return getRuleContext(Not_formulaContext.class,0);
		}
		public Always_formulaContext always_formula() {
			return getRuleContext(Always_formulaContext.class,0);
		}
		public Eventually_formulaContext eventually_formula() {
			return getRuleContext(Eventually_formulaContext.class,0);
		}
		public Until_formulaContext until_formula() {
			return getRuleContext(Until_formulaContext.class,0);
		}
		public And_formulaContext and_formula() {
			return getRuleContext(And_formulaContext.class,0);
		}
		public Or_formulaContext or_formula() {
			return getRuleContext(Or_formulaContext.class,0);
		}
		public Imply_formulaContext imply_formula() {
			return getRuleContext(Imply_formulaContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_formula);
		try {
			setState(40);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(28);
				match(LPAREN);
				setState(29);
				formula();
				setState(30);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(32);
				atom_formula();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(33);
				not_formula();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(34);
				always_formula();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(35);
				eventually_formula();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(36);
				until_formula();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(37);
				and_formula();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(38);
				or_formula();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(39);
				imply_formula();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Imply_formulaContext extends ParserRuleContext {
		public TerminalNode IMPLY() { return getToken(MTLParser.IMPLY, 0); }
		public List<Or_formulaContext> or_formula() {
			return getRuleContexts(Or_formulaContext.class);
		}
		public Or_formulaContext or_formula(int i) {
			return getRuleContext(Or_formulaContext.class,i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(MTLParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(MTLParser.LPAREN, i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(MTLParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(MTLParser.RPAREN, i);
		}
		public Imply_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_imply_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterImply_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitImply_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitImply_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Imply_formulaContext imply_formula() throws RecognitionException {
		Imply_formulaContext _localctx = new Imply_formulaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_imply_formula);
		try {
			setState(58);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(42);
					or_formula();
					}
					break;
				case 2:
					{
					setState(43);
					match(LPAREN);
					setState(44);
					formula();
					setState(45);
					match(RPAREN);
					}
					break;
				}
				setState(49);
				match(IMPLY);
				setState(55);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(50);
					or_formula();
					}
					break;
				case 2:
					{
					setState(51);
					match(LPAREN);
					setState(52);
					formula();
					setState(53);
					match(RPAREN);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				or_formula();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Or_formulaContext extends ParserRuleContext {
		public TerminalNode OR() { return getToken(MTLParser.OR, 0); }
		public List<And_formulaContext> and_formula() {
			return getRuleContexts(And_formulaContext.class);
		}
		public And_formulaContext and_formula(int i) {
			return getRuleContext(And_formulaContext.class,i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(MTLParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(MTLParser.LPAREN, i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(MTLParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(MTLParser.RPAREN, i);
		}
		public Or_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterOr_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitOr_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitOr_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Or_formulaContext or_formula() throws RecognitionException {
		Or_formulaContext _localctx = new Or_formulaContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_or_formula);
		try {
			setState(76);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(65);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(60);
					and_formula();
					}
					break;
				case 2:
					{
					setState(61);
					match(LPAREN);
					setState(62);
					formula();
					setState(63);
					match(RPAREN);
					}
					break;
				}
				setState(67);
				match(OR);
				setState(73);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(68);
					and_formula();
					}
					break;
				case 2:
					{
					setState(69);
					match(LPAREN);
					setState(70);
					formula();
					setState(71);
					match(RPAREN);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				and_formula();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class And_formulaContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(MTLParser.AND, 0); }
		public List<Until_formulaContext> until_formula() {
			return getRuleContexts(Until_formulaContext.class);
		}
		public Until_formulaContext until_formula(int i) {
			return getRuleContext(Until_formulaContext.class,i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(MTLParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(MTLParser.LPAREN, i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(MTLParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(MTLParser.RPAREN, i);
		}
		public And_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterAnd_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitAnd_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitAnd_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final And_formulaContext and_formula() throws RecognitionException {
		And_formulaContext _localctx = new And_formulaContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_and_formula);
		try {
			setState(94);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
				case 1:
					{
					setState(78);
					until_formula();
					}
					break;
				case 2:
					{
					setState(79);
					match(LPAREN);
					setState(80);
					formula();
					setState(81);
					match(RPAREN);
					}
					break;
				}
				setState(85);
				match(AND);
				setState(91);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(86);
					until_formula();
					}
					break;
				case 2:
					{
					setState(87);
					match(LPAREN);
					setState(88);
					formula();
					setState(89);
					match(RPAREN);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(93);
				until_formula();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Until_formulaContext extends ParserRuleContext {
		public TerminalNode UNTIL() { return getToken(MTLParser.UNTIL, 0); }
		public List<Eventually_formulaContext> eventually_formula() {
			return getRuleContexts(Eventually_formulaContext.class);
		}
		public Eventually_formulaContext eventually_formula(int i) {
			return getRuleContext(Eventually_formulaContext.class,i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(MTLParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(MTLParser.LPAREN, i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(MTLParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(MTLParser.RPAREN, i);
		}
		public TerminalNode LBRACKET() { return getToken(MTLParser.LBRACKET, 0); }
		public List<TerminalNode> INTEGER() { return getTokens(MTLParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MTLParser.INTEGER, i);
		}
		public TerminalNode COMMA() { return getToken(MTLParser.COMMA, 0); }
		public TerminalNode RBRACKET() { return getToken(MTLParser.RBRACKET, 0); }
		public Until_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_until_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterUntil_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitUntil_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitUntil_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Until_formulaContext until_formula() throws RecognitionException {
		Until_formulaContext _localctx = new Until_formulaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_until_formula);
		int _la;
		try {
			setState(119);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				switch (_input.LA(1)) {
				case NOT:
				case ALWAYS:
				case EVENTUALLY:
				case CONST_TRUTH:
				case ID:
					{
					setState(96);
					eventually_formula();
					}
					break;
				case LPAREN:
					{
					setState(97);
					match(LPAREN);
					setState(98);
					formula();
					setState(99);
					match(RPAREN);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(103);
				match(UNTIL);
				setState(109);
				_la = _input.LA(1);
				if (_la==LBRACKET) {
					{
					setState(104);
					match(LBRACKET);
					setState(105);
					match(INTEGER);
					setState(106);
					match(COMMA);
					setState(107);
					_la = _input.LA(1);
					if ( !(_la==T__0 || _la==INTEGER) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(108);
					match(RBRACKET);
					}
				}

				setState(116);
				switch (_input.LA(1)) {
				case NOT:
				case ALWAYS:
				case EVENTUALLY:
				case CONST_TRUTH:
				case ID:
					{
					setState(111);
					eventually_formula();
					}
					break;
				case LPAREN:
					{
					setState(112);
					match(LPAREN);
					setState(113);
					formula();
					setState(114);
					match(RPAREN);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				eventually_formula();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Eventually_formulaContext extends ParserRuleContext {
		public TerminalNode EVENTUALLY() { return getToken(MTLParser.EVENTUALLY, 0); }
		public Always_formulaContext always_formula() {
			return getRuleContext(Always_formulaContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MTLParser.LPAREN, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MTLParser.RPAREN, 0); }
		public TerminalNode LBRACKET() { return getToken(MTLParser.LBRACKET, 0); }
		public List<TerminalNode> INTEGER() { return getTokens(MTLParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MTLParser.INTEGER, i);
		}
		public TerminalNode COMMA() { return getToken(MTLParser.COMMA, 0); }
		public TerminalNode RBRACKET() { return getToken(MTLParser.RBRACKET, 0); }
		public Eventually_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eventually_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterEventually_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitEventually_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitEventually_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Eventually_formulaContext eventually_formula() throws RecognitionException {
		Eventually_formulaContext _localctx = new Eventually_formulaContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_eventually_formula);
		int _la;
		try {
			setState(137);
			switch (_input.LA(1)) {
			case EVENTUALLY:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				match(EVENTUALLY);
				setState(127);
				_la = _input.LA(1);
				if (_la==LBRACKET) {
					{
					setState(122);
					match(LBRACKET);
					setState(123);
					match(INTEGER);
					setState(124);
					match(COMMA);
					setState(125);
					_la = _input.LA(1);
					if ( !(_la==T__0 || _la==INTEGER) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(126);
					match(RBRACKET);
					}
				}

				setState(134);
				switch (_input.LA(1)) {
				case NOT:
				case ALWAYS:
				case CONST_TRUTH:
				case ID:
					{
					setState(129);
					always_formula();
					}
					break;
				case LPAREN:
					{
					setState(130);
					match(LPAREN);
					setState(131);
					formula();
					setState(132);
					match(RPAREN);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case NOT:
			case ALWAYS:
			case CONST_TRUTH:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				always_formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Always_formulaContext extends ParserRuleContext {
		public TerminalNode ALWAYS() { return getToken(MTLParser.ALWAYS, 0); }
		public Not_formulaContext not_formula() {
			return getRuleContext(Not_formulaContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MTLParser.LPAREN, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MTLParser.RPAREN, 0); }
		public TerminalNode LBRACKET() { return getToken(MTLParser.LBRACKET, 0); }
		public List<TerminalNode> INTEGER() { return getTokens(MTLParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MTLParser.INTEGER, i);
		}
		public TerminalNode COMMA() { return getToken(MTLParser.COMMA, 0); }
		public TerminalNode RBRACKET() { return getToken(MTLParser.RBRACKET, 0); }
		public Always_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_always_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterAlways_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitAlways_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitAlways_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Always_formulaContext always_formula() throws RecognitionException {
		Always_formulaContext _localctx = new Always_formulaContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_always_formula);
		int _la;
		try {
			setState(155);
			switch (_input.LA(1)) {
			case ALWAYS:
				enterOuterAlt(_localctx, 1);
				{
				setState(139);
				match(ALWAYS);
				setState(145);
				_la = _input.LA(1);
				if (_la==LBRACKET) {
					{
					setState(140);
					match(LBRACKET);
					setState(141);
					match(INTEGER);
					setState(142);
					match(COMMA);
					setState(143);
					_la = _input.LA(1);
					if ( !(_la==T__0 || _la==INTEGER) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(144);
					match(RBRACKET);
					}
				}

				setState(152);
				switch (_input.LA(1)) {
				case NOT:
				case CONST_TRUTH:
				case ID:
					{
					setState(147);
					not_formula();
					}
					break;
				case LPAREN:
					{
					setState(148);
					match(LPAREN);
					setState(149);
					formula();
					setState(150);
					match(RPAREN);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case NOT:
			case CONST_TRUTH:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				not_formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Not_formulaContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(MTLParser.NOT, 0); }
		public Atom_formulaContext atom_formula() {
			return getRuleContext(Atom_formulaContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MTLParser.LPAREN, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MTLParser.RPAREN, 0); }
		public Not_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_not_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterNot_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitNot_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitNot_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Not_formulaContext not_formula() throws RecognitionException {
		Not_formulaContext _localctx = new Not_formulaContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_not_formula);
		try {
			setState(166);
			switch (_input.LA(1)) {
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(157);
				match(NOT);
				setState(163);
				switch (_input.LA(1)) {
				case CONST_TRUTH:
				case ID:
					{
					setState(158);
					atom_formula();
					}
					break;
				case LPAREN:
					{
					setState(159);
					match(LPAREN);
					setState(160);
					formula();
					setState(161);
					match(RPAREN);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case CONST_TRUTH:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(165);
				atom_formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Atom_formulaContext extends ParserRuleContext {
		public TerminalNode CONST_TRUTH() { return getToken(MTLParser.CONST_TRUTH, 0); }
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public Atom_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterAtom_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitAtom_formula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitAtom_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Atom_formulaContext atom_formula() throws RecognitionException {
		Atom_formulaContext _localctx = new Atom_formulaContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_atom_formula);
		try {
			setState(170);
			switch (_input.LA(1)) {
			case CONST_TRUTH:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				match(CONST_TRUTH);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				symbol();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SymbolContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MTLParser.ID, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).enterSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MTLListener ) ((MTLListener)listener).exitSymbol(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MTLVisitor ) return ((MTLVisitor<? extends T>)visitor).visitSymbol(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23\u00b1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\3\2\3\2\3\2\3\2\5\2\35\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\5\3+\n\3\3\4\3\4\3\4\3\4\3\4\5\4\62\n\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\5\4:\n\4\3\4\5\4=\n\4\3\5\3\5\3\5\3\5\3\5\5\5D\n\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\5\5L\n\5\3\5\5\5O\n\5\3\6\3\6\3\6\3\6\3\6\5\6V\n\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\5\6^\n\6\3\6\5\6a\n\6\3\7\3\7\3\7\3\7\3\7\5\7"+
		"h\n\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7p\n\7\3\7\3\7\3\7\3\7\3\7\5\7w\n\7\3"+
		"\7\5\7z\n\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0082\n\b\3\b\3\b\3\b\3\b\3\b"+
		"\5\b\u0089\n\b\3\b\5\b\u008c\n\b\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0094\n\t"+
		"\3\t\3\t\3\t\3\t\3\t\5\t\u009b\n\t\3\t\5\t\u009e\n\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\5\n\u00a6\n\n\3\n\5\n\u00a9\n\n\3\13\3\13\5\13\u00ad\n\13\3\f\3"+
		"\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\3\3\2\3\4\u00c4\2\34\3\2\2\2"+
		"\4*\3\2\2\2\6<\3\2\2\2\bN\3\2\2\2\n`\3\2\2\2\fy\3\2\2\2\16\u008b\3\2\2"+
		"\2\20\u009d\3\2\2\2\22\u00a8\3\2\2\2\24\u00ac\3\2\2\2\26\u00ae\3\2\2\2"+
		"\30\35\7\2\2\3\31\32\5\4\3\2\32\33\7\2\2\3\33\35\3\2\2\2\34\30\3\2\2\2"+
		"\34\31\3\2\2\2\35\3\3\2\2\2\36\37\7\6\2\2\37 \5\4\3\2 !\7\7\2\2!+\3\2"+
		"\2\2\"+\5\24\13\2#+\5\22\n\2$+\5\20\t\2%+\5\16\b\2&+\5\f\7\2\'+\5\n\6"+
		"\2(+\5\b\5\2)+\5\6\4\2*\36\3\2\2\2*\"\3\2\2\2*#\3\2\2\2*$\3\2\2\2*%\3"+
		"\2\2\2*&\3\2\2\2*\'\3\2\2\2*(\3\2\2\2*)\3\2\2\2+\5\3\2\2\2,\62\5\b\5\2"+
		"-.\7\6\2\2./\5\4\3\2/\60\7\7\2\2\60\62\3\2\2\2\61,\3\2\2\2\61-\3\2\2\2"+
		"\62\63\3\2\2\2\639\7\r\2\2\64:\5\b\5\2\65\66\7\6\2\2\66\67\5\4\3\2\67"+
		"8\7\7\2\28:\3\2\2\29\64\3\2\2\29\65\3\2\2\2:=\3\2\2\2;=\5\b\5\2<\61\3"+
		"\2\2\2<;\3\2\2\2=\7\3\2\2\2>D\5\n\6\2?@\7\6\2\2@A\5\4\3\2AB\7\7\2\2BD"+
		"\3\2\2\2C>\3\2\2\2C?\3\2\2\2DE\3\2\2\2EK\7\f\2\2FL\5\n\6\2GH\7\6\2\2H"+
		"I\5\4\3\2IJ\7\7\2\2JL\3\2\2\2KF\3\2\2\2KG\3\2\2\2LO\3\2\2\2MO\5\n\6\2"+
		"NC\3\2\2\2NM\3\2\2\2O\t\3\2\2\2PV\5\f\7\2QR\7\6\2\2RS\5\4\3\2ST\7\7\2"+
		"\2TV\3\2\2\2UP\3\2\2\2UQ\3\2\2\2VW\3\2\2\2W]\7\13\2\2X^\5\f\7\2YZ\7\6"+
		"\2\2Z[\5\4\3\2[\\\7\7\2\2\\^\3\2\2\2]X\3\2\2\2]Y\3\2\2\2^a\3\2\2\2_a\5"+
		"\f\7\2`U\3\2\2\2`_\3\2\2\2a\13\3\2\2\2bh\5\16\b\2cd\7\6\2\2de\5\4\3\2"+
		"ef\7\7\2\2fh\3\2\2\2gb\3\2\2\2gc\3\2\2\2hi\3\2\2\2io\7\20\2\2jk\7\b\2"+
		"\2kl\7\4\2\2lm\7\5\2\2mn\t\2\2\2np\7\t\2\2oj\3\2\2\2op\3\2\2\2pv\3\2\2"+
		"\2qw\5\16\b\2rs\7\6\2\2st\5\4\3\2tu\7\7\2\2uw\3\2\2\2vq\3\2\2\2vr\3\2"+
		"\2\2wz\3\2\2\2xz\5\16\b\2yg\3\2\2\2yx\3\2\2\2z\r\3\2\2\2{\u0081\7\17\2"+
		"\2|}\7\b\2\2}~\7\4\2\2~\177\7\5\2\2\177\u0080\t\2\2\2\u0080\u0082\7\t"+
		"\2\2\u0081|\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0088\3\2\2\2\u0083\u0089"+
		"\5\20\t\2\u0084\u0085\7\6\2\2\u0085\u0086\5\4\3\2\u0086\u0087\7\7\2\2"+
		"\u0087\u0089\3\2\2\2\u0088\u0083\3\2\2\2\u0088\u0084\3\2\2\2\u0089\u008c"+
		"\3\2\2\2\u008a\u008c\5\20\t\2\u008b{\3\2\2\2\u008b\u008a\3\2\2\2\u008c"+
		"\17\3\2\2\2\u008d\u0093\7\16\2\2\u008e\u008f\7\b\2\2\u008f\u0090\7\4\2"+
		"\2\u0090\u0091\7\5\2\2\u0091\u0092\t\2\2\2\u0092\u0094\7\t\2\2\u0093\u008e"+
		"\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u009a\3\2\2\2\u0095\u009b\5\22\n\2"+
		"\u0096\u0097\7\6\2\2\u0097\u0098\5\4\3\2\u0098\u0099\7\7\2\2\u0099\u009b"+
		"\3\2\2\2\u009a\u0095\3\2\2\2\u009a\u0096\3\2\2\2\u009b\u009e\3\2\2\2\u009c"+
		"\u009e\5\22\n\2\u009d\u008d\3\2\2\2\u009d\u009c\3\2\2\2\u009e\21\3\2\2"+
		"\2\u009f\u00a5\7\n\2\2\u00a0\u00a6\5\24\13\2\u00a1\u00a2\7\6\2\2\u00a2"+
		"\u00a3\5\4\3\2\u00a3\u00a4\7\7\2\2\u00a4\u00a6\3\2\2\2\u00a5\u00a0\3\2"+
		"\2\2\u00a5\u00a1\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a9\5\24\13\2\u00a8"+
		"\u009f\3\2\2\2\u00a8\u00a7\3\2\2\2\u00a9\23\3\2\2\2\u00aa\u00ad\7\21\2"+
		"\2\u00ab\u00ad\5\26\f\2\u00ac\u00aa\3\2\2\2\u00ac\u00ab\3\2\2\2\u00ad"+
		"\25\3\2\2\2\u00ae\u00af\7\22\2\2\u00af\27\3\2\2\2\32\34*\619<CKNU]`go"+
		"vy\u0081\u0088\u008b\u0093\u009a\u009d\u00a5\u00a8\u00ac";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}