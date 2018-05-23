package com.dnf.test.struct;

import java.util.Arrays;

import com.dnf.test.model.TermRvs;


public class IntObjectArray {

	private ObjectArray<TermRvs>[] arrays;

	private int n;

	public IntObjectArray() {

	}

	@SuppressWarnings("unchecked")
	public IntObjectArray(int n) {
		arrays = new ObjectArray[n];
	}

	public void add(int index, ObjectArray<TermRvs> array) {
		if (index > arrays.length) {
			resize(arrays.length);
		}
		arrays[index] = array;
		n++;
	}

	public ObjectArray<TermRvs> get(int index) {
		assert index < n;
		return arrays[index];
	}

	public int getSize() {
		return n;
	}

	private void resize(int max) {
		int oldLength = arrays.length;
		int newLength = oldLength + (oldLength >> 1);
		if (newLength - max < 0) {
			newLength = max;
		}
		this.arrays = Arrays.copyOf(arrays, newLength);
	}

}
