/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     BOOLEAN = 258,
     ID = 259,
     NUMBER = 260,
     NR = 261,
     CHARACTER = 262,
     STRING = 263,
     ADDITION = 264,
     SUBTRACTION = 265,
     MULTIPLICATION = 266,
     DIV = 267,
     MOD = 268,
     ASSIGN = 269,
     IS = 270,
     GREATER = 271,
     GREATER_EQUAL = 272,
     LESS = 273,
     LESS_EQUAL = 274,
     AND = 275,
     OR = 276,
     NOT = 277,
     DOT = 278,
     DOTS = 279,
     SEMI_COLUMN = 280,
     OPEN_BRACKET = 281,
     CLOSED_BRACKET = 282,
     CST = 283,
     VERIFY = 284,
     YES = 285,
     NO = 286,
     PRINT = 287,
     VAR = 288,
     READ = 289,
     RETURN = 290,
     LIST = 291,
     GO = 292,
     AS = 293,
     WITH = 294
   };
#endif
/* Tokens.  */
#define BOOLEAN 258
#define ID 259
#define NUMBER 260
#define NR 261
#define CHARACTER 262
#define STRING 263
#define ADDITION 264
#define SUBTRACTION 265
#define MULTIPLICATION 266
#define DIV 267
#define MOD 268
#define ASSIGN 269
#define IS 270
#define GREATER 271
#define GREATER_EQUAL 272
#define LESS 273
#define LESS_EQUAL 274
#define AND 275
#define OR 276
#define NOT 277
#define DOT 278
#define DOTS 279
#define SEMI_COLUMN 280
#define OPEN_BRACKET 281
#define CLOSED_BRACKET 282
#define CST 283
#define VERIFY 284
#define YES 285
#define NO 286
#define PRINT 287
#define VAR 288
#define READ 289
#define RETURN 290
#define LIST 291
#define GO 292
#define AS 293
#define WITH 294




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

