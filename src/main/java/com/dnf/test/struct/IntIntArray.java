package com.dnf.test.struct;

import java.util.Arrays;

public class IntIntArray {

	private IntArray[] intArrays;

	private int n;

	public IntIntArray() {
		this(4);
	}

	public IntIntArray(int n) {
		this.intArrays = new IntArray[n];
		this.n = 0;
	}

	public void add(IntArray intArray) {
		if (n == intArrays.length) {
			resize(n);
		}
		intArrays[n++] = intArray;
	}

	public IntArray get(int index) {
		assert index < n;
		return intArrays[index];
	}

	public void add(int index, int value) {
		intArrays[index].add(value);
	}

	private void resize(int max) {
		int oldLength = intArrays.length;
		int newLength = oldLength + (oldLength >> 1);
		if (newLength - max < 0) {
			newLength = max;
		}
		this.intArrays = Arrays.copyOf(intArrays, newLength);
	}

	public int size() {
		return n;
	}
}
