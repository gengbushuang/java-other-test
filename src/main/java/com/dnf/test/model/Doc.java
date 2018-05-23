package com.dnf.test.model;

import com.dnf.test.struct.IntArray;

/**
 * 
 * @author gengbushuang
 *
 *
 *         doc: (age ∈ { 3, 4 } and state ∈ { NY } ) or ( state ∈ { CA } and
 *         gender ∈ {M } ) -->
 * 
 *         conj1: (age ∈ { 3, 4 } and state ∈ { NY } )
 * 
 *         conj2: ( state ∈ { CA } and gender ∈ { M } )
 */
public class Doc {

	private int id;

	// private int docId;

	private String name;

	private String dnf;

	private boolean conjSort;

	private IntArray conjs;

	// public Doc() {
	// this(0, new IntArray());
	// }

	public Doc(int id, String name, String dnf, IntArray intArray) {
		this.id = id;
		this.name = name;
		this.dnf = dnf;
		this.conjs = intArray;
	}

	public void addConjId(int conjId) {
		conjs.add(conjId);
	}

}
