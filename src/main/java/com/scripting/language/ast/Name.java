package com.scripting.language.ast;

import com.scripting.language.Token;

public class Name extends ASTLear {

	public Name(Token t) {
		super(t);
	}

	public String name() {
		return this.getToken().getText();
	}

}
