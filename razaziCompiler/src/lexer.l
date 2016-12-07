
%{
#include <stdio.h>
#include "types.h"
#include "parser.tab.h"


extern FILE *yyin;
int yylineno;

char ** sm;
int sm_pointer = 0;

%}


PROGRAM_KW			(program)
MAIN_KW				(main)
PROCEDURE_KW		(proc)
REAL_KW				(real)
DIGIT				[0-9]
LETTER				[a-zA-Z]
INTEGER_KW			(int)
CHAR_KW				(char)
IF_KW				(if)
BOOLEAN_KW			(bool)
THEN_KW				(then)
ELSE_KW				(else)
DO_KW				(do)
WHILE_KW			(while)
FOR_KW				(for)
IN_KW				(in)
REPEAT_KW			(repeat)
CASE_KW				(case)
SWITCH_KW			(switch)
DEFAULT_KW			(default)
END_KW				(end)
RETURN_KW			(return)
EXIT_KW				(exit)
WHEN_KW				(when)
INTEGER_CONSTANT	(-?)[0-9]+
BOOLEAN_CONSTANT	(true|false)
REAL_CONTANT		[0-9]*\.[0-9]+
CHAR_CONSTANT		(\'[0-9a-zA-Z]\')
LT_KW				(<)
LE_KW				(<=)
GT_KW				(>)
GE_KW				(>=)
EQ_KW				(=)
NE_KW				(<>)
AND_KW				(and)
OR_KW				(or)
NOT_KW				(not)
PLUS				(\+)
MIUNS				(-)
MULTIPLY			(\*)
DIVISION			(\/)
MODULE				(%)
ASSIGN				(:=)
COLON				(:)
SEMI_COLON			(;)
O_C_BRACE			(\{)
C_C_BRACE			(\})
O_BRACE				(\[)
C_BRACE				(\])
O_PARENTHESIS		(\()
C_PARENTHESIS		(\))
DOTS				(\.\.)
COMMA				(,)
IDENTIFIER			{LETTER}+|{LETTER}({LETTER}|{DIGIT})*
NEW_LINE			(\\r\\n)

%%
{PROGRAM_KW}    	{return PROGRAM_KW;}   
{MAIN_KW}    		{return MAIN_KW;}    	
{PROCEDURE_KW}    	{return PROCEDURE_KW;} 
{REAL_KW}    		{return REAL_KW;}    	
{INTEGER_KW}    	{return INTEGER_KW;}   
{CHAR_KW}    		{return CHAR_KW;}    	
{IF_KW}    			{return IF_KW;}    		
{BOOLEAN_KW}		{return BOOLEAN_KW;}	
{THEN_KW}    		{return THEN_KW;}    	
{ELSE_KW}    		{return ELSE_KW;}    	
{DO_KW}    			{return DO_KW;}    		
{WHILE_KW}    		{return WHILE_KW;}    	
{FOR_KW}    		{return FOR_KW;}    	
{IN_KW}    			{return IN_KW;}  
{REPEAT_KW}  		{return REPEAT_KW;}
{CASE_KW}    		{return CASE_KW;}    	
{SWITCH_KW}    		{return SWITCH_KW;}    
{DEFAULT_KW}    	{return DEFAULT_KW;} 
{END_KW}    		{return END_KW;}   
{RETURN_KW}    		{return RETURN_KW;}    
{EXIT_KW}    		{return EXIT_KW;}    	
{WHEN_KW}    		{return WHEN_KW;}    	
{BOOLEAN_CONSTANT}  {return BOOLEAN_CONSTANT;}
{INTEGER_CONSTANT}  {return INTEGER_CONSTANT;}
{CHAR_CONSTANT}    	{return CHAR_CONSTANT;}
{REAL_CONTANT}    	{return REAL_CONTANT;} 
{LT_KW}    			{return LT_KW;}    		
{LE_KW}    			{return LE_KW;}    		
{GT_KW}    			{return GT_KW;}    		
{GE_KW}   		 	{return GE_KW;}   		
{EQ_KW}    			{return EQ_KW;}    		
{NE_KW}   		 	{return NE_KW;}   		
{AND_KW}    		{return AND_KW;}    	
{OR_KW}   		 	{return OR_KW;}   		
{NOT_KW}    		{return NOT_KW;}    	
{PLUS}   		 	{return PLUS;}   		
{MIUNS}    			{return MIUNS;}    		
{MULTIPLY}   		{return MULTIPLY;}   	
{DIVISION}   		{return DIVISION;}   	
{MODULE}   			{return MODULE;}   		
{ASSIGN}   			{return ASSIGN;}   		
{COLON}   			{return COLON;}   		
{SEMI_COLON}		{return SEMI_COLON;}	
{O_C_BRACE}   		{return O_C_BRACE;}   	
{C_C_BRACE}   		{return C_C_BRACE;}   	
{O_BRACE}   		{return O_BRACE;}   	
{C_BRACE}   		{return C_BRACE;}   	
{O_PARENTHESIS}   	{return O_PARENTHESIS;}
{C_PARENTHESIS}   	{return C_PARENTHESIS;}
{DOTS}   			{return DOTS;}   		
{COMMA}   			{return COMMA;}   		
{IDENTIFIER}   		{return IDENTIFIER;} 
. {}
%%

