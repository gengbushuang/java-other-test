package com.scripting.language.analyzer;

import com.scripting.language.Token;

public class NumToken extends Token {

	private int num;

	protected NumToken(int line, int number) {
		super(line);
		this.num = number;
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public int getNumber() {
		return num;
	}

	@Override
	public String getText() {
		return String.valueOf(num);
	}

}
