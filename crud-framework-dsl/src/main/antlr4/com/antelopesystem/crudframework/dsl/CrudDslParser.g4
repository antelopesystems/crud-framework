parser grammar CrudDslParser;
options { tokenVocab=CrudDslLexer; }
query: QUERY O_CURLY where C_CURLY EOF;

where: WHERE O_CURLY (predicate)* C_CURLY;

predicate: comparison EOL | junction;
comparison: standardComparison | stringComparison | numericalOrDateComparison | arrayComparison | betweenComparison;

betweenComparison: left=ID op=OP_BETWEEN first=numericalValue AND second=numericalValue;
standardComparison: left=ID op=(OP_EQ | OP_NE) right=anyValue;
stringComparison: left=ID op=(OP_CONTAINS | OP_STARTSWITH | OP_ENDSWITH) right=stringValue;
numericalOrDateComparison: left=ID op=(OP_GT | OP_GTE | OP_LT | OP_LTE) right=numericalValue;
arrayComparison: left=ID op=(OP_IN | OP_NOT_IN) right=arrayValue;


junction: type=(OR | AND | NOT) O_CURLY (predicate)* C_CURLY;

arrayValue: (functionCall | O_SQUARE (anyValue (COMMA anyValue)*) C_SQUARE);
booleanValue: (functionCall | BOOLEAN_VALUE);
numericalValue: (functionCall | INT_VALUE);
stringValue: (functionCall | STRING_VALUE);
anyValue: STRING_VALUE | INT_VALUE | BOOLEAN_VALUE | NULL_VALUE | functionCall;

functionCall: name=ID O_ROUND (anyValue? (COMMA anyValue)*?) C_ROUND;