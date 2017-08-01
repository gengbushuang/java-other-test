package com.scripting.language.ast;

import com.scripting.language.Token;

public class NumberLiteral extends ASTLear {

	public NumberLiteral(Token t) {
		super(t);
	}
	
	public int getValue(){
		return this.getToken().getNumber();
	}

}
