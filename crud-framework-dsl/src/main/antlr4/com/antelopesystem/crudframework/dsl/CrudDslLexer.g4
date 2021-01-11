lexer grammar CrudDslLexer;

fragment ID_LETTER : [a-z] | [A-Z] | '_' | DOT | FORWARD_SLASH;
fragment DIGIT : [0-9] ;

O_CURLY: '{';
C_CURLY: '}';
O_SQUARE: '[';
C_SQUARE: ']';
O_ROUND: '(';
C_ROUND: ')';
COMMA: ',';
FORWARD_SLASH: '/';
DOT: '.';

QUERY: 'query';
WHERE: 'where';

OP_EQ: '==';
OP_NE: '!=';
OP_CONTAINS: '~';
OP_IN: 'in';
OP_NOT_IN: 'not in';
OP_GT: '>';
OP_GTE: '>=';
OP_LT: '<';
OP_LTE: '<=';
OP_STARTSWITH: '^';
OP_ENDSWITH: '$';
OP_BETWEEN: 'between';

AND: 'and';
OR: 'or';
NOT: 'not';

EOL: ';';


BOOLEAN_VALUE: TRUE | FALSE;
NULL_VALUE: 'null';
STRING_VALUE: '\''.*?'\'';
INT_VALUE: ('0' | (MINUS | PLUS)? [1-9] [0-9]*);
TRUE: 'true';
FALSE: 'false';
MINUS: '-';
PLUS: '+';

LINE_COMMENT: '#' ~( '\r' | '\n' )* -> channel(HIDDEN);
WS: [ \n\t\r]+ -> skip;
ID: (ID_LETTER (ID_LETTER | DIGIT)*);