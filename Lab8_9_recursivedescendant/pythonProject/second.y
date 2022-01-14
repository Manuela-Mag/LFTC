%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define YYDEBUG 1

int yylex();
void yyerror(char *s);
%}


%token BOOLEAN
%token ID
%token NUMBER
%token NR
%token CHARACTER
%token STRING
%token ADDITION
%token SUBTRACTION
%token MULTIPLICATION
%token DIV
%token MOD
%token ASSIGN
%token IS
%token GREATER
%token GREATER_EQUAL
%token LESS
%token LESS_EQUAL
%token AND
%token OR
%token NOT
%token DOT
%token DOTS
%token SEMI_COLUMN
%token OPEN_BRACKET
%token CLOSED_BRACKET

%token CST
%token VERIFY
%token YES
%token NO
%token PRINT
%token VAR
%token READ
%token RETURN
%token LIST
%token GO
%token AS
%token WITH


%start program

%%
program : declist programC ;
programC : /* empty */ | stmtlist ;
declist : declaration SEMI_COLUMN declistC ;
declistC : /* empty */ | declist ;
declaration : type ID ;
type : VAR | CST ;
stmtlist : stmt SEMI_COLUMN stmtlistC ;
stmtlistC : /* empty */ | stmtlist ;
stmt : simplestmt | structstmt ;
simplestmt : assignstmt | iostmt ;
assignstmt : ID ASSIGN expr ;
expr : term exprC ;
exprC : /* empty */ | exprOp expr ;
exprOp : ADDITION | SUBTRACTION ;
term : factor termC ;
termC : /* empty */ | termOP term ;
termOP : MULTIPLICATION | DIV | MOD ;
factor : ID | NR ;
iostmt : READ ID | PRINT ID ;
structstmt : ifstmt | forstmt ;
ifstmt : VERIFY cond DOTS YES DOTS stmtlist ifstmtC ;
ifstmtC : /* empty */ | NO DOTS stmtlist ;
cond : term rel term ;
rel : IS | GREATER | GREATER_EQUAL | LESS | LESS_EQUAL ;
forstmt : GO assignstmt AS cond WITH assignstmt DOTS stmt ;
%%
void yyerror(char *s)
{
printf("%s\n",s);
}

extern FILE *yyin;

int main(int argc, char **argv)
{
if(argc>1) yyin : fopen(argv[1],"r");
if(argc>2 && !strcmp(argv[2],"-d")) yydebug: 1;
if(!yyparse()) fprintf(stderr, "\tO.K.\n");
}
