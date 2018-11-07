grammar MTL;

@header {
package se.liu.ida.jprogress.parser;
}

start
        : EOF
        | formula EOF ;

// Precedence order:
// not, and, or, ->, forall, exists, next, always, eventually, until

// Evaluates to boolean value, i.e. true/false(/unknown)
formula
        : LPAREN formula RPAREN
        | atom_formula
        | not_formula
        | always_formula
        | eventually_formula
        | until_formula
        | and_formula
        | or_formula
        | imply_formula
        ;

imply_formula
        : (or_formula | LPAREN formula RPAREN) IMPLY (or_formula | LPAREN formula RPAREN)
        | or_formula
        ;

or_formula
        : (and_formula | LPAREN formula RPAREN) OR (and_formula | LPAREN formula RPAREN)
        | and_formula
        ;

and_formula
        : (until_formula | LPAREN formula RPAREN) AND (until_formula | LPAREN formula RPAREN)
        | until_formula
        ;

until_formula
        : (eventually_formula | LPAREN formula RPAREN) UNTIL (LBRACKET INTEGER COMMA (INTEGER | 'oo') RBRACKET)? (eventually_formula | LPAREN formula RPAREN)
        | eventually_formula
        ;

eventually_formula
        : EVENTUALLY (LBRACKET INTEGER COMMA (INTEGER | 'oo') RBRACKET)? (always_formula | LPAREN formula RPAREN)
        | always_formula
        ;

always_formula
        : ALWAYS (LBRACKET INTEGER COMMA (INTEGER | 'oo') RBRACKET)? (not_formula | LPAREN formula RPAREN)
        | not_formula
        ;

not_formula
        : NOT (atom_formula | LPAREN formula RPAREN)
        | atom_formula
        ;

atom_formula
        : CONST_TRUTH
        | symbol
        ;


INTEGER         : [0-9]+ ;
COMMA           : ',';
LPAREN          : '(' ;
RPAREN          : ')' ;
LBRACKET        : '[' ;
RBRACKET        : ']' ;


NOT         : 'not' ;
AND         : 'and' ;
OR          : 'or' ;
IMPLY       : '->' ;
ALWAYS      : 'always' ;
EVENTUALLY  : 'eventually' ;
UNTIL       : 'until' ;



CONST_TRUTH
        : 'true'
        | 'false'
        ;


ID              : [a-zA-Z_][a-zA-Z_0-9]* ;

symbol: ID;

WS              : [ \t\r\n]+ -> skip;