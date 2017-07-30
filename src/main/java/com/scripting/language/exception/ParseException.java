package com.scripting.language.exception;

import java.io.IOException;

import com.scripting.language.Token;

public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4555575559675780472L;

	public ParseException(Token t) {
		this("", t);
	}

	public ParseException(String msg, Token t) {
		super("syntax error around " + location(t) + ". " + msg);
	}

	private static String location(Token t) {
		if (t == Token.EOF) {
			return "the last line";
		} else {
			return "\"" + t.getText() + "\" at line " + t.getLineNumber();
		}
	}

	public ParseException(IOException e) {
		super(e);
	}

	public ParseException(String msg) {
		super(msg);
	}

}
