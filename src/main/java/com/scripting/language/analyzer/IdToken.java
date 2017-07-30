package com.scripting.language.analyzer;

import com.scripting.language.Token;

public class IdToken extends Token {

	private String identifier;

	protected IdToken(int line, String id) {
		super(line);
		this.identifier = id;
	}

	@Override
	public boolean isIdentifier() {
		return true;
	}

	@Override
	public String getText() {
		return identifier;
	}
}
