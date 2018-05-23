package com.dnf.test.model;

import com.dnf.test.struct.IntArray;

/**
 * 
 * @author gengbushuang
 *
 *         assignment: age ∈ { 3, 4 } -->
 * 
 *         term1: age ∈ { 3 }
 * 
 *         term2: age ∈ { 4 }
 *
 */
public class Assignment {
	// Assignment的索引ID
	private int id;

	// 是否排除
	private boolean isEliminate;

	// 是否排序
	private boolean termSort;

	private IntArray terms;

	public Assignment(boolean isEliminate) {
		this(new IntArray(), isEliminate);
	}

	public Assignment(IntArray intArray, boolean isEliminate) {
		this(0, intArray, isEliminate);
	}

	public Assignment(int id, IntArray intArray, boolean isEliminate) {
		this.isEliminate = isEliminate;
		this.terms = intArray;
		this.id = id;
	}

	public IntArray getTerms() {
		return terms;
	}

	public boolean isEliminate() {
		return isEliminate;
	}
	
	public void addTermId(int termId) {
		terms.add(termId);
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isEliminate ? 1231 : 1237);
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
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
		Assignment other = (Assignment) obj;
		if (isEliminate != other.isEliminate)
			return false;
		if (terms == null) {
			if (other.terms != null)
				return false;
		} else if (!terms.equals(other.terms))
			return false;
		return true;
	}
}
