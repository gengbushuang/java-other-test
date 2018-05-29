package com.dnf.reverse2.model;

import java.io.Serializable;

public class Pair<K, V> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1905049644990267560L;

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
