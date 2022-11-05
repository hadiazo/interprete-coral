grammar MiLenguaje;

// REGLAS SINTACTICAS
inicio : TKN_STRING | expr;
expr   :  term ( (TKN_PLUS | TKN_MINUS) term)*;
term   :  factor ( (TKN_TIMES | TKN_DIV | TKN_MOD) factor)*;
factor :  TKN_INTEGER | TKN_FLOAT;

// TOKENS
TKN_PERIOD: '.';
TKN_COMMA: ',';
TKN_SEMICOLON: ';';
TKN_CLOSING_BRA: ']';
TKN_OPENING_BRA: '[';
TKN_CLOSING_PAR: ')';
TKN_OPENING_PAR: '(';
TKN_PLUS: '+';
TKN_MINUS: '-';
TKN_TIMES: '*';
TKN_DIV: '/';
TKN_MOD: '%';
TKN_QUESTION_MARK: '?';
TKN_ASSIGN: '=';
TKN_LESS: '<';
TKN_GREATER: '>';
TKN_EQUAL: '==';
TKN_NEQ: '!=';
TKN_LEQ: '<=';
TKN_GEQ: '>=';
GET: 'Get';
NEXT: 'next';
INPUT: 'input';
PUT: 'Put';
TO: 'to';
OUTPUT: 'output';
IF: 'if';
ELSEIF: 'elseif';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
INTEGER: 'integer';
FLOAT: 'float';
ARRAY: 'array';
FUNCTION: 'Function';
RETURNS: 'returns';
SQUAREROOT: 'SquareRoot';
RAISETOPOWER: 'RaiseToPower';
ABSOLUTEVALUE: 'AbsoluteValue';
RANDOMNUMBER: 'RandomNumber';
SEEDRANDOMNUMBERS: 'SeedRandomNumbers';
WITH: 'with';
DECIMAL: 'decimal';
PLACES: 'places';
MAIN: 'Main';
SIZE: 'size';
OR: 'or';
AND: 'and';
NOT: 'not';
NOTHING: 'nothing';

// REGLAS LEXICAS
TKN_INTEGER:'0'..'9'+;
TKN_FLOAT: [0-9]+.[0-9]+;
TKN_STRING : '"' (~["\\]|'\\'.)* '"';
ESPACIO: [ \t\r\n]+ -> skip;
ID: [a-zA-Z][a-zA-Z0-9_]*;
