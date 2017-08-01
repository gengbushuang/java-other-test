package com.scripting.language.ast;

import java.util.Iterator;
import java.util.List;

import com.scripting.language.ASTree;

public class ASTList extends ASTree {

	private List<ASTree> children;

	public ASTList(List<ASTree> children) {
		this.children = children;
	}

	@Override
	public ASTree child(int i) {
		return children.get(i);
	}

	@Override
	public int childNumber() {
		return children.size();
	}

	@Override
	public Iterator<ASTree> children() {
		return children.iterator();
	}

	@Override
	public String location() {
		for (ASTree asTree : children) {
			String location = asTree.location();
			if (location != null) {
				return location;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ASTree asTree : children) {
			if (sb.length() > 0) {
				sb.append(" ");
			} else {
				sb.append("(");
			}
			sb.append(asTree.toString());
		}
		sb.append(")");
		return sb.toString();
	}
}
