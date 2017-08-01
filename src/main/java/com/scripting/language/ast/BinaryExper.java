package com.scripting.language.ast;

import java.util.List;

import com.scripting.language.ASTree;

public class BinaryExper extends ASTList {

	public BinaryExper(List<ASTree> children) {
		super(children);
	}

	public ASTree getLeft() {
		return this.child(0);
	}

	public String operator() {
		return ((ASTLear) this.child(1)).getToken().getText();
	}

	public ASTree getRight() {
		return this.child(2);
	}

}
