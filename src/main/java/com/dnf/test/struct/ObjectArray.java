package com.dnf.test.struct;

import java.util.Arrays;

public class ObjectArray<T> {

	private Object[] objects;

	private int n;

	public ObjectArray() {
		this(10);
	}

	public ObjectArray(int n) {
		this.objects = new Object[n];
		this.n = 0;
	}

	public void add(T t) {
		if (n == objects.length) {
			resize(n);
		}
		objects[n++] = t;
	}

	public T get(int index) {
		assert index < n;
		return (T) objects[index];
	}

	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n == 0;
	}
	
	public void search(int key) {
		
	}

	private void resize(int max) {
		int oldLength = objects.length;
		int newLength = oldLength + (oldLength >> 1);
		if (newLength - max < 0) {
			newLength = max;
		}
		this.objects = Arrays.copyOf(objects, newLength);
	}

}
