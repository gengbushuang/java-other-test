package com.scripting.language;

import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree> {

	public abstract ASTree child(int i);

	public abstract int childNumber();

	public abstract Iterator<ASTree> children();

	public abstract String location();

	@Override
	public Iterator<ASTree> iterator() {
		return children();
	}
}
