%{

#include <stdio.h>
#include <fstream>
#include "types.h"
#include "parser.tab.h"
using namespace std;

// stuff from flex that bison needs to know about:
//extern "C" int yylex();
//extern "C" int yyparse();
extern FILE *yyin;
extern char* yytext;
FILE *fout;
extern int yylex();

extern int yylineno;
void yyerror(const char *s);

vector<blockEntry> symol_stack;


blockEntry lookUp(blockEntry table,string id ){
	if(!table) return NULL;
	for (int i = 0; i < table->id_entries.size(); ++i){
		if(!id.compare(table->id_entries[i].name)) return table;
	}
	return lookUp(table->backward,id);
}

void finish(){
	fclose(fout);
	fclose(yyin);
	exit(0);
}


// void assign_var(val* a, val* b){
// 	a->name = b->name;
// 	a->int_vals = b->int_vals;
// 	a->char_vals = b->char_vals;
// 	a->real_vals =  b->real_vals;
// }

%}// Bison fundamentally works by asking flex to get the next token, which it
// returns as an object of type "yystype".  But tokens could be of any
// arbitrary data type!  So we deal with that in Bison by defining a C union
// holding each of the types of tokens that Flex could return, and have Bison
// use that union instead of "int" for the definition of "yystype":
%union {
	val* eval =  new val();
}



// define the "terminal symbol" token types I'm going to use (in CAPS
// by convention), and associate each with a field of the union:

%token PROGRAM_KW MAIN_KW PROCEDURE_KW REAL_KW INTEGER_KW CHAR_KW IF_KW BOOLEAN_KW THEN_KW ELSE_KW DO_KW WHILE_KW FOR_KW REPEAT_KW IN_KW 
%token CASE_KW SWITCH_KW DEFAULT_KW END_KW RETURN_KW EXIT_KW WHEN_KW LT_KW LE_KW GT_KW GE_KW EQ_KW NE_KW AND_KW OR_KW NOT_KW PLUS MIUNS MULTIPLY 
%token DIVISION MODULE ASSIGN COLON SEMI_COLON O_C_BRACE C_C_BRACE O_BRACE C_BRACE O_PARENTHESIS C_PARENTHESIS DOTS COMMA NEW_LINE

%token <eval> INTEGER_CONSTANT REAL_CONTANT CHAR_CONSTANT BOOLEAN_CONSTANT IDENTIFIER


%type <eval> type_specifiers dec end_initializer_list declarator end_declarator obrace_initializer obrace_initializer_list initializer_list declarator_list range arithmetic_expressions constant_expressions expressions declarations
            
%left AND_KW OR_KW
%right ASSIGN
%left EQ_KW NE_KW
%left LT_KW GT_KW
%left LE_KW GE_KW
%left PLUS MIUNS
%left MULTIPLY DIVISION MODULE
%right NOT_KW
%left THEN_KW
%left ELSE_KW

%%

// this is the actual grammar that bison will parse, but for right now it's just
// something silly to echo to the screen what bison gets from flex.  We'll
// make a real one shortly:
program : PROGRAM_KW IDENTIFIER M_symbol_table declarations_list procedure_list MAIN_KW block 
	{
		fprintf(fout, "Rule 1 \t\t program -> PROGRAM_KW IDENTIFIER declarations_list procedure_list MAIN_KW block \n") ;
	};
	| PROGRAM_KW IDENTIFIER M_symbol_table MAIN_KW block 
	{
		fprintf(fout, "Rule 1 \t\t program -> PROGRAM_KW IDENTIFIER block \n") ;
	};
	| PROGRAM_KW IDENTIFIER M_symbol_table declarations_list MAIN_KW block 
	{
		fprintf(fout, "Rule 1 \t\t program -> PROGRAM_KW IDENTIFIER declarations_list block \n") ;
	};
	| PROGRAM_KW IDENTIFIER M_symbol_table procedure_list MAIN_KW block 
	{
		fprintf(fout, "Rule 1 \t\t program -> PROGRAM_KW IDENTIFIER procedure_list block \n") ;
	};

M_symbol_table : {
	blockEntry a = new table_st();
	a->backward = NULL;
	symol_stack.push_back(a);

}		
declarations_list : declarations
	{
		fprintf(fout, "Rule 2 \t\t declarations_list -> declarations \n") ;
	};
	|  declarations_list declarations
	{
		fprintf(fout, "Rule 2 \t\t declarations_list -> declarations_list declarations \n") ;
	};
declarations : type_specifiers declarator_list
	{
		// $2->type = $1->type;
		fprintf(fout, "Rule 3 \t\t declarations -> type_specifiers declarator_list\n") ;
	}
	
type_specifiers : INTEGER_KW
	{
		// $$->type = "integer";
		fprintf(fout, "Rule 4 \t\t type_specifiers -> INTEGER_KW\n") ;
	};
	| CHAR_KW
	{
		// $$->type = "char";
		fprintf(fout, "Rule 4 \t\t type_specifiers -> CHAR_KW\n") ;
	};
	| BOOLEAN_KW
	{
		// $$->type = "bool";
		fprintf(fout, "Rule 4 \t\t type_specifiers -> BOOLEAN_KW\n") ;
	};
	| REAL_KW
	{
		// $$->type = "real";
		fprintf(fout, "Rule 4 \t\t type_specifiers -> REAL_KW\n") ;
	};

declarator_list : end_declarator
	{
		// $$->ids.push_back($1);
		fprintf(fout, "Rule 5 \t\t declarator_list -> end_declarator \n") ;
	};
	| declarator declarator_list 
	{
		// $$->ids.push_back($1);
		// for(int i = 0; i<$2->ids.size(); i++) {
  //   		// $$->ids.push_back($2->ids[i]);
		// }
		fprintf(fout, "Rule 5 \t\t declarator_list -> declarator , declarator_list \n") ;
	};
end_declarator : dec SEMI_COLON
	{
		// $$ = $1;
		fprintf(fout, "Rule 5 \t\t end_declarator -> dec; \n") ;
	}
	| dec ASSIGN obrace_initializer SEMI_COLON
	{
		// $$->ivalues= $3->ivalues;
		// $$->fvalues= $3->fvalues;
		// $$->name = $1->name;
		// $$->type = $3->type;
		fprintf(fout, "Rule 6 \t\t end_declarator -> dec := obrace_initializer; \n") ;
	}
	|
	dec C_PARENTHESIS{
		// $$ = $1;
		fprintf(fout, "Rule 5 \t\t end_declarator -> dec) \n") ;
	}
	| dec ASSIGN obrace_initializer C_PARENTHESIS
	{
		// $$->ivalues= $3->ivalues;
		// $$->fvalues= $3->fvalues;
		// $$->name = $1->name;
		// $$->type = $3->type;
		fprintf(fout, "Rule 6 \t\t end_declarator -> dec := obrace_initializer) \n") ;
	}
	| dec ASSIGN end_initializer_list
	{
	// 	$$->name = $1->name;
	// 	$$->type = $3->type;
	// 	$$->ivalues = $3->ivalues;
	// 	$$->fvalues= $3->fvalues;
		fprintf(fout, "Rule 6 \t\t end_declarator -> dec := end_initializer_list \n") ;
	};
	| dec ASSIGN initializer_list end_initializer_list
	{
		// if ($3->type != $4->type){
		// 	cout<<"Type Error : "<<$3->type<<" != "<<$4->type<<endl;
		// 	fprintf(fout,"Type Error : %s,%s\n",$3->type,$4->type);
		// 	finish();
		// }
		// $$->ivalues= $3->ivalues;
		// $$->fvalues= $3->fvalues;
		// for(int i = 0; i<$4->fvalues.size(); i++) {
  //   		$$->fvalues.push_back($4->fvalues[i]);
		// }
		// for(int i = 0; i<$4->ivalues.size(); i++) {
  //   		$$->ivalues.push_back($4->ivalues[i]);
		// }
		// $$->name = $1->name;
		// $$->type = $3->type;
		fprintf(fout, "Rule 6 \t\t end_declarator -> dec := initializer_list end_initializer_list \n") ;
	};


declarator : dec COMMA
	{
		// $$ = $1;
		fprintf(fout, "Rule 6 \t\t declarator -> dec, \n") ;
	};
	| dec ASSIGN obrace_initializer COMMA
	{
		// $$->ivalues = $3->ivalues;
		// $$->fvalues = $3->fvalues;
		// $$->name = $1->name;
		// $$->type = $3->type;
		fprintf(fout, "Rule 6 \t\t declarator -> dec := {initializer}, \n") ;
	}
	| dec ASSIGN initializer_list
	{
		//$$->name = $1->name;
		//$$->begin = $1->begin;
		//$$->end = $1->end;
		//$$->type = $3->type;
		fprintf(fout, "Rule 6 \t\t declarator -> dec := initializer_list \n") ;
	};
	
dec : IDENTIFIER
	{
		//$$->name = $1->named;
		fprintf(fout, "Rule 7 \t\t dec -> IDENTIFIER \n") ;
	};
	| IDENTIFIER O_BRACE range C_BRACE
	{
		//$$->name = $1->named;
		//$$->begin = $3->begin;
		//$$->end = $3->end;
		fprintf(fout, "Rule 7 \t\t dec -> IDENTIFIER[range] \n") ;
	};
	| IDENTIFIER O_BRACE INTEGER_CONSTANT C_BRACE
	{
		//$$->name = $1->named;
		//$$->begin = 0;
		//$$->end = $3->value;
		fprintf(fout, "Rule 7 \t\t dec -> IDENTIFIER[INTEGER_CONST] \n") ;
	};
	
range : IDENTIFIER DOTS IDENTIFIER
	{

		fprintf(fout, "Rule 8 \t\t range -> IDENTIFIER .. IDENTIFIER \n") ;
	};
	| INTEGER_CONSTANT DOTS INTEGER_CONSTANT
	{
		//$$->begin = $1->value;
		//$$->end = $3->value;
		fprintf(fout, "Rule 8 \t\t range -> INTEGER_CONSTANT .. INTEGER_CONSTANT \n") ;
	};
	| arithmetic_expressions DOTS arithmetic_expressions
	{
		// if ($1->type != int_t || $3->type != int_t){
		// 	fprintf(fout,"Type Error! range bounds must be integer");
		// 	finish();
		// }
		//$$->begin = $1->i;
		//$$->end = $3->i;
		fprintf(fout, "Rule 8 \t\t range -> arithmetic_expressions .. arithmetic_expressions \n") ;	
	};
obrace_initializer : O_C_BRACE obrace_initializer_list C_C_BRACE
	{
		// $$ = $2;
		fprintf(fout, "Rule 9 \t\t obrace_initializer -> {obrace_initializer_list} \n") ;
	};
obrace_initializer_list: constant_expressions COMMA obrace_initializer_list
	{
		// if ($1->type != $3->type){
		// 	fprintf(fout,"Type Error : %d,%d\n",$1->type,$3->type);
		// 	finish();
		// }
		// $$->type = $1->type;
		// insertValueToVarStruct($$,$1);	
		fprintf(fout, "Rule 10 \t obrace_initializer_list -> constant_expressions , obrace_initializer_list \n") ;
	};
	| constant_expressions
	{
		// $$->type = $1->type;
		// insertValueToVarStruct($$,$1);
		fprintf(fout, "Rule 10 \t obrace_initializer_list -> constant_expressions \n") ;	
	};
initializer_list: initializer_list constant_expressions COMMA
	{
		// if ($1->type != $2->type){
		// 	fprintf(fout,"Type Error : %d,%d\n",$1->type,$2->type);
		// 	finish();
		// }
		// $$->type = $1->type;
		// insertValueToVarStruct($$,$2);
		fprintf(fout, "Rule 10 \t initializer_list -> initializer_list constant_expressions ,\n") ;
	};
	| constant_expressions COMMA
	{
		// $$->type = $1->type;
		// insertValueToVarStruct($$,$1);
		fprintf(fout, "Rule 10 \t initializer_list -> constant_expressions, \n") ;	
	}
	|
	end_initializer_list
	{
		// $$->type = $1->type;
		// insertValueToVarStruct($$,$1);
		fprintf(fout, "Rule 10 \t initializer_list -> end_initializer_list, \n") ;
	};

end_initializer_list: constant_expressions SEMI_COLON
	{
		// $$ = $1;
		fprintf(fout, "Rule 10 \t end_initializer_list -> constant_expressions; \n") ;	
	}
	|
	constant_expressions C_PARENTHESIS
	{
		// $$ = $1;
		fprintf(fout, "Rule 10 \t end_initializer_list -> constant_expressions) \n") ;	
	};


procedure_list : procedure_list procedure
	{
		fprintf(fout, "Rule 11 \t procedure_list -> procedure_list procedure\n") ;		
	};
procedure_list : procedure
	{
		fprintf(fout, "Rule 11 \t procedure_list -> procedure\n") ;		
	};
procedure : PROCEDURE_KW IDENTIFIER parameters O_C_BRACE declarations_list block C_C_BRACE SEMI_COLON
	{
		fprintf(fout, "Rule 12.1 \t procedure -> PROCEDURE_KW IDENTIFIER parameters { declarations_list block}; \n")
	}
	|
	PROCEDURE_KW IDENTIFIER parameters O_C_BRACE block C_C_BRACE SEMI_COLON
	{
		fprintf(fout, "Rule 12.2 \t procedure -> PROCEDURE_KW IDENTIFIER parameters { block }; \n") ;
	};
	
parameters : O_PARENTHESIS declarations_list C_PARENTHESIS
	{
		fprintf(fout, "Rule 13 \t parameters -> (declarations_list) \n") ;
	};
block : O_C_BRACE statement_list C_C_BRACE
	{
		fprintf(fout, "Rule 14 \t block -> {statement_list} \n") ;
	};
statement_list : statement SEMI_COLON
	{
		fprintf(fout, "Rule 15 \t statement_list -> statement; \n") ; 
	};
	| statement_list statement SEMI_COLON
	{
		fprintf(fout, "Rule 15 \t statement_list -> statement_list statement; \n") ; 
	};
	// remove ambguity for statement -> empty
	| statement_list SEMI_COLON
	{
		fprintf(fout, "Rule 15 \t statement_list -> statement_list ; \n") ; 
	}
	| SEMI_COLON
	{
		fprintf(fout, "Rule 15 \t statement_list -> ; \n") ; 	
	}
	//

statement : IDENTIFIER ASSIGN expressions
	{
		fprintf(fout, "Rule 16 \t statement -> IDENTIFIER := expressions \n") ; 
	};
	| IF_KW bool_expressions THEN_KW statement ELSE_KW statement
	{
		fprintf(fout, "Rule 16 \t statement -> if bool_expressions then statement else statement \n") ; 	
	};
	| IF_KW bool_expressions THEN_KW statement
	{
		fprintf(fout, "Rule 16 \t statement -> if bool_expressions then statement \n") ; 	
	};
	// remove ambguity for statement -> empty
	| IF_KW bool_expressions THEN_KW
	{
		fprintf(fout, "Rule 16 \t statement -> if bool_expressions then \n") ; 	
	};

	// remove ambguity for statement -> empty
	| IF_KW bool_expressions THEN_KW  ELSE_KW 
	{
		fprintf(fout, "Rule 16 \t statement -> if bool_expressions then else \n") ; 	
	};
	// remove ambguity for statement -> empty
	| IF_KW bool_expressions THEN_KW ELSE_KW statement
	{
		fprintf(fout, "Rule 16 \t statement -> if bool_expressions then else statement \n") ; 	
	};
	// remove ambguity for statement -> empty
	| IF_KW bool_expressions THEN_KW statement ELSE_KW 
	{
		fprintf(fout, "Rule 16 \t statement -> if bool_expressions then statement else \n") ; 	
	};

	| DO_KW statement WHILE_KW bool_expressions
	{
		fprintf(fout, "Rule 16 \t statement -> do statement while bool_expressions\n") ;
	};
	// remove ambguity for statement -> empty
	| DO_KW WHILE_KW bool_expressions
	{
		fprintf(fout, "Rule 16 \t statement -> do while bool_expressions\n") ;
	};

	| FOR_KW IDENTIFIER IN_KW range REPEAT_KW statement
	{
		fprintf(fout, "Rule 16 \t statement -> for IDENTIFIER in range loop statement \n") ;
	};
	// remove ambguity for statement -> empty
	| FOR_KW IDENTIFIER IN_KW range REPEAT_KW
	{
		fprintf(fout, "Rule 16 \t statement -> for IDENTIFIER in range loop \n") ;
	};
	| SWITCH_KW expressions caseelement default END_KW
	{
		fprintf(fout, "Rule 16 \t statement -> switch expressions caseelement default end \n") ;
	};
	//remove ambguity for default -> empty
	| SWITCH_KW expressions caseelement END_KW
	{
		fprintf(fout, "Rule 16 \t statement -> switch expressions caseelement end \n") ;
	};
	| IDENTIFIER O_PARENTHESIS arguments
	{
		fprintf(fout, "Rule 16 \t statement -> IDENTIFIER (arguments \n") ;
	};
	// remove ambguity for arguments -> empty
	| IDENTIFIER O_PARENTHESIS C_PARENTHESIS
	{
		fprintf(fout, "Rule 16 \t statement -> IDENTIFIER () \n") ;
	};
	| RETURN_KW expressions
	{
		fprintf(fout, "Rule 16 \t statement -> return expressions \n") ;
	};
	| EXIT_KW WHEN_KW  O_PARENTHESIS bool_expressions C_PARENTHESIS
	{
		fprintf(fout, "Rule 16 \t statement -> exit when (bool_expressions) \n") ;
	};
	| block
	{
		fprintf(fout, "Rule 16 \t statement -> block \n") ;
	}
arguments : declarator_list
	{
		fprintf(fout, "Rule 17 \t arguments -> declarator_list \n") ;	
	};
caseelement : CASE_KW INTEGER_CONSTANT COLON block SEMI_COLON
	{
		fprintf(fout, "Rule 18 \t caseelement -> case INTEGER_CONSTANT : block ; \n") ;		
	};
	| caseelement CASE_KW INTEGER_CONSTANT COLON block SEMI_COLON
	{
		fprintf(fout, "Rule 18 \t caseelement -> caseelement case INTEGER_CONSTANT: block ; \n") ;		
	};
default : DEFAULT_KW COLON block SEMI_COLON
	{
		fprintf(fout, "Rule 19 \t default -> default : block; \n") ;		
	};
expressions : constant_expressions
	{
		//$$->type = $1->type;
		//$$->i = $1->i;
		//$$->c = $1->c;
		//$$->r = $1->r;
		//$$->b = $1->b;
		fprintf(fout, "Rule 20 \t expressions -> constant_expressions \n") ;
	}
	| bool_expressions
	{
		fprintf(fout, "Rule 20 \t expressions -> bool_expressions \n") ;
	};
	| arithmetic_expressions
	{
		//$$->type = $1->type;
		//$$->i = $1->i;
		//$$->c = $1->c;
		//$$->r = $1->r;
		//$$->b = $1->b;
		fprintf(fout, "Rule 20 \t expressions -> arithmetic_expressions \n") ;
	};
	| IDENTIFIER
	{
		fprintf(fout, "Rule 20 \t expressions -> IDENTIFIER \n") ;	
	}
	| O_PARENTHESIS expressions C_PARENTHESIS
	{
		//$$->type = $2->type;
		//$$->i = $2->i;
		//$$->c = $2->c;
		//$$->r = $2->r;
		//$$->b = $2->b;
		fprintf(fout, "Rule 20 \t expressions -> (expressions) \n") ;	
	};
constant_expressions : INTEGER_CONSTANT
	{
		//$$->type = int_t;
		//$$->i = $1->value;
		fprintf(fout, "Rule 21 \t constant_expressions -> INTEGER_CONSTANT \n") ;	
	}
	| REAL_CONTANT
	{
		//$$->type = real_t;
		//$$->r = $1->value;
		fprintf(fout, "Rule 21 \t constant_expressions -> REAL_CONTANT \n") ;		
	};
	| CHAR_CONSTANT
	{
		//$$->type = char_t;
		//$$->c = $1->value;
		fprintf(fout, "Rule 21 \t constant_expressions -> CHAR_CONSTANT \n") ;		
	};
	| BOOLEAN_CONSTANT
	{
		//$$->type = bool_t;
		//$$->b = $1->value;
		fprintf(fout, "Rule 21 \t constant_expressions -> BOOLEAN_CONSTANT \n") ;		
	};
bool_expressions : expressions LT_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions < expressions \n") ;		
	};
	| expressions LE_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions <= expressions \n") ;		
	};
	| expressions GT_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions > expressions \n") ;		
	};
	| expressions GE_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions >= expressions \n") ;		
	};
	| expressions EQ_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions = expressions \n") ;		
	};
	| expressions NE_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions <> expressions \n") ;		
	};
	| expressions AND_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions and expressions \n") ;		
	};
	| expressions OR_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions or expressions \n") ;		
	};
	| expressions AND_KW THEN_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions and then expressions \n") ;		
	};
	| expressions OR_KW ELSE_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> expressions or else expressions \n") ;		
	};
	| NOT_KW expressions
	{
		fprintf(fout, "Rule 22 \t bool_expressions -> not expressions \n") ;		
	};
arithmetic_expressions : expressions PLUS expressions
	{
		fprintf(fout, "Rule 23 \t arithmetic_expressions -> expressions + expressions \n") ;			
	};
	| expressions MIUNS expressions
	{
		fprintf(fout, "Rule 23 \t arithmetic_expressions -> expressions - expressions \n") ;			
	};
	| expressions MULTIPLY expressions
	{
		fprintf(fout, "Rule 23 \t arithmetic_expressions -> expressions * expressions \n") ;			
	};
	| expressions DIVISION expressions
	{
		fprintf(fout, "Rule 23 \t arithmetic_expressions -> expressions / expressions \n") ;			
	};
	| expressions MODULE expressions
	{
		fprintf(fout, "Rule 23 \t arithmetic_expressions -> expressions %% expressions \n") ;			
	};
	| MIUNS expressions
	{
		fprintf(fout, "Rule 23 \t arithmetic_expressions -> - expressions \n") ;			
	};
%%

int main(int argc, char** argv) {
	if(argc < 2) {
		printf("wrong input");
		return 0;
	}
	printf("%s\n",argv[1] );
	//return 0;
	yyin = fopen(argv[1], "r");	
	fout = fopen("output.txt", "w");
	fprintf(fout, "PARSER \n\n");
	fprintf(fout, "Rule No.\t\t --> Rule Description \n");
	
	if (fout == NULL)
	{
		printf("Error opening file!\n");
		//exit(1);
	}
	// make sure it is valid:
	else if (!yyin) {
		printf("Error opening file!\n");
		//exit(1);
	}
	// set flex to read from it instead of defaulting to STDIN:
	
	// parse through the input until there is no more:
	else
		yyparse();
}


void yyerror(char const *s) {
	fprintf(fout, "**Error:  --> Message: %s in line %d on %s **\n", s, yylineno, yytext);
	printf("**Error:  --> Message: %s in line %d on %s **\n", s, yylineno, yytext);
}