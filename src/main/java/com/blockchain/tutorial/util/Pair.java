package com.blockchain.tutorial.util;

public class Pair<K, V> {

	private K k;

	private V v;

	public Pair(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public K _1() {
		return k;
	}

	public V _2() {
		return v;
	}
}
