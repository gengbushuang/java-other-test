package com.scripting.language.analyzer;

import com.scripting.language.Token;
import com.scripting.language.exception.ParseException;

public class LexerRunner {
	public static void main(String[] args) throws ParseException {
		Lexer l = new Lexer(new CodeDialog());
		for(Token t;(t=l.read())!=Token.EOF;){
			System.out.println("=>"+t.getText());
		}
	}
}
