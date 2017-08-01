package com.scripting.language.ast;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.scripting.language.ASTree;
import com.scripting.language.Token;

public class ASTLear extends ASTree {

	private List<ASTree> empty = Collections.emptyList();

	private Token t;

	public ASTLear(Token t) {
		this.t = t;
	}

	@Override
	public ASTree child(int i) {
		return null;
	}

	@Override
	public int childNumber() {
		return 0;
	}

	@Override
	public Iterator<ASTree> children() {
		return empty.iterator();
	}

	@Override
	public String location() {
		return "at line " + t.getLineNumber();
	}

	public Token getToken() {
		return t;
	}

	@Override
	public String toString() {
		return t.getText();
	}

}
