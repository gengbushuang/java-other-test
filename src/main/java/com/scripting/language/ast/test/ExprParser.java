package com.scripting.language.ast.test;

import java.util.Arrays;

import com.scripting.language.ASTree;
import com.scripting.language.Token;
import com.scripting.language.analyzer.CodeDialog;
import com.scripting.language.analyzer.Lexer;
import com.scripting.language.ast.ASTLear;
import com.scripting.language.ast.BinaryExper;
import com.scripting.language.ast.NumberLiteral;
import com.scripting.language.exception.ParseException;

/*
 * factor     : NUMBER | "(" expression ")"
 * term       : factor { ("*" | "/") factor }
 * expression : term { ("+" | "-") term }
 */

public class ExprParser {
	private Lexer lexer;

	public ExprParser(Lexer l) {
		lexer = l;
	}

	public ASTree expression() throws ParseException {
		ASTree left = term();
		while (isToken("+") || isToken("-")) {
			ASTLear op = new ASTLear(lexer.read());
			ASTree right = term();
			left = new BinaryExper(Arrays.asList(left, op, right));
		}
		return left;
	}

	public ASTree term() throws ParseException {
		ASTree left = factor();
		while (isToken("*") || isToken("/")) {
			ASTLear op = new ASTLear(lexer.read());
			ASTree right = factor();
			left = new BinaryExper(Arrays.asList(left, op, right));
		}
		return left;
	}

	public ASTree factor() throws ParseException {
		if (isToken("(")) {
			token("(");
			ASTree e = expression();
			token(")");
			return e;
		} else {
			Token t = lexer.read();
			if (t.isNumber()) {
				NumberLiteral n = new NumberLiteral(t);
				return n;
			} else {
				throw new ParseException(t);
			}
		}
	}

	void token(String name) throws ParseException {
		Token t = lexer.read();
		if (!(t.isIdentifier() && name.equals(t.getText()))) {
			throw new ParseException(t);
		}
	}

	boolean isToken(String name) throws ParseException {
		Token t = lexer.peek(0);
		return t.isIdentifier() && name.equals(t.getText());
	}
	
	public static void main(String[] args) throws ParseException {
		Lexer lexer = new Lexer(new CodeDialog());
		ExprParser p = new ExprParser(lexer);
		ASTree t = p.expression();
		System.out.println("=> "+t);
	}
}
