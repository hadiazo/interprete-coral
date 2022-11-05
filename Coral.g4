grammar Coral;

tokens { INDENT, DEDENT }

///Palabras reservadas
GET      :'Get';
NEXT     :'next';
INPUT    :'input';
PUT      :'Put';
TO       :'to';
OUTPUT   :'output';
IF       :'if';
ELSEIF   :'elseif';
ELSE     :'else';
WHILE    :'while';
FOR      :'for';
INTEGER  :'integer';
FLOAT    :'float';
ARRAY    :'array';
FUNCTION :'Function';
RETURNS  :'returns';
SQUAREROOT        :'SquareRoot';
RAISETOPOWER      :'RaiseToPower';
ABSOLUTEVALUE     :'AbsoluteValue';
RANDOMNUMBER      :'RandomNumber';
SEEDRANDOMNUMBERS :'SeedRandomNumbers';
WITH     :'with';
DECIMAL  :'decimal';
PLACES   :'places';
SIZE     :'size';
MAIN     :'Main';
OR       :'or';
AND      :'and';
NOT      :'not';
NOTHING  :'nothing';


//TOKENS
///Operadores
OPERADOR:PLUS|MINUS|TIMES|DIV|MOD;
PLUS   :  '+';
MINUS  :  '-';
TIMES  :  '*';
DIV    :  '/';
MOD    :  '%';
ASSIGN :  '=';
PERIOD :  '.';
COMA   :  ',';
SEMICOLON     :  ';';
OPENING_PAR   :  '(';
CLOSSING_PAR  :  ')';
OPENING_BAR   :  '[';
CLOSSING_BAR  :  ']';
EQUAL  :  '==';
NEQ    :  '!=';
LESS   :  '<';
LEQ    :  '<=';
GRATER :  '>';
GEQ    :  '>=';
QUESTION_MARK :  '?';

//COMILLAS :  '"';

//Reglas Lexicas
NUMERO: INT | FLOATN;
INT: '0'..'9'+;
FLOATN:[0-9]+('.'[0-9])+;
ID: [a-zA-Z][a-zA-Z_0-9]* ;
ESPACIO: [ \t\r\n]+ -> skip;
CADENA:  '"' ( ~["]*'\\' . )* '"';



//Reglas Sintacticas
init: funcion | declaracion |imprimir ;

tipo: INTEGER | FLOAT  ;
funcion: FUNCTION (ID)OPENING_PAR declaracionF+ CLOSSING_PAR RETURNS declaracion;
declaracion: tipo ID | tipo array ID | NOTHING;
declaracionF: tipo ID | tipo array ID ;
operacion: OPENING_PAR operacion CLOSSING_PAR OPERADOR NUMERO |  NUMERO OPERADOR OPENING_PAR operacion CLOSSING_PAR
         | ID OPERADOR ID | NUMERO OPERADOR NUMERO | ID OPERADOR NUMERO | NUMERO OPERADOR ID;
asignacion: ID ASSIGN NUMERO |ID ASSIGN operacion;
array: ARRAY OPENING_PAR '?' CLOSSING_PAR | ARRAY OPENING_PAR INT CLOSSING_PAR;
imprimir: PUT ID TO OUTPUT | PUT CADENA TO OUTPUT;

