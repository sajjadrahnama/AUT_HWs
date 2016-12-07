#!/bin/bash

cd src
flex lexer.l
bison -d parser.y
g++ lex.yy.c parser.tab.c -std=c++11 -ll -o ../compiler
rm lex.yy.c
rm parser.tab.h
rm parser.tab.h.gch
rm parser.tab.c
cd ..


