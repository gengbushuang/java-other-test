package com.dnf.test.struct;

import java.util.Arrays;

public class IntArray {

	private int[] indexs;

	private int n;

	public IntArray() {
		this(4);
	}

	public IntArray(int n) {
		this.indexs = new int[n];
		this.n = 0;
	}

	public void add(int value) {
		if (n == indexs.length) {
			resize(n);
		}
		indexs[n++] = value;
	}

	public int get(int index) {
		assert index < n;
		return indexs[index];
	}

	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int search(int key) {
		return Arrays.binarySearch(indexs, key);
	}

	private void resize(int max) {
		int oldLength = indexs.length;
		int newLength = oldLength + (oldLength >> 1);
		if (newLength - max < 0) {
			newLength = max;
		}
		this.indexs = Arrays.copyOf(indexs, newLength);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(indexs);
		result = prime * result + n;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntArray other = (IntArray) obj;
		if (n != other.n)
			return false;
		if (!Arrays.equals(indexs, other.indexs))
			return false;
		return true;
	}
}