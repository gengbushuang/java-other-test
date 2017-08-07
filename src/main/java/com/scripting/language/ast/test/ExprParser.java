package com.scripting.language.ast.test;

import java.util.Arrays;

import com.scripting.language.ASTree;
import com.scripting.language.analyzer.Lexer;
import com.scripting.language.ast.ASTLear;
import com.scripting.language.ast.BinaryExper;

/*
 * factor     : NUMBER | "(" expression ")"
 * term       : factor { ("*" | "/") factor }
 * expression : term { ("+" | "-") term }
 */

public class ExprParser {
//	private Lexer lexer;
//
//	public ExprParser(Lexer l) {
//		lexer = l;
//	}
//	
//	public ASTree expression(){
//		ASTree left = term();
//		while(isToken("+") || isToken("-")){
//			ASTLear op = new ASTLear(lexer.read());
//			ASTree right = term();
//			left = new BinaryExper(Arrays.asList(left,op,right));
//		}
//	}
//	
//	public ASTree term(){
//		ASTree left = factor();
//		while(isToken("*") || isToken("/")){
//			ASTLear op = new ASTLear(lexer.read());
//			ASTree right = factor();
//			left = new BinaryExper(Arrays.asList(left,op,right));
//		}
//		return left;
//	}
//	
//	public ASTree factor(){
//		if(isToken("(")){
//			token("(");
//			ASTree e = expression();
//			token(")");
//			return e;
//		}else{
//			
//		}
//	}
}
