package com.scripting.language;

import com.scripting.language.exception.StoneException;

public abstract class Token {

	public static final Token EOF = new Token(-1){};
	public static final String EOL = "\\n";
	
	private int lineNumber;

	protected Token(int line) {
		this.lineNumber = line;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	//标识符
	public boolean isIdentifier(){
		return false;
	}
	//整型字面量
	public boolean isNumber(){
		return false;
	}
	//字符串字面量
	public boolean isString(){
		return false;
	}
	
	public int getNumber(){
		throw new StoneException("not number token");
	}
	
	public String getText(){
		return "";
	}
}
