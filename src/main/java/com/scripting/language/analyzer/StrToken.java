package com.scripting.language.analyzer;

import com.scripting.language.Token;

public class StrToken extends Token {

	private String str;

	protected StrToken(int line, String str) {
		super(line);
		this.str = str;
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public String getText() {
		return str;
	}
}
