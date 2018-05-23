package com.dnf.test.model;

import com.dnf.test.struct.IntArray;

/**
 * 
 * @author gengbushuang
 *
 *         conjunction: age ∈ { 3, 4 } and state ∈ { NY } -->
 * 
 *         assignment1: age ∈ { 3, 4 }
 * 
 *         assignment2: state ∈ { NY }
 */
public class Conjunction {

	// Conjunction的索引ID
	private int id;

	private int size;

	private boolean amtSort;
	// 保存Amt的索引
	private IntArray amts;

	public Conjunction() {
		this(new IntArray());
	}

	public Conjunction(IntArray intArray) {
		this(0, intArray);
	}

	public Conjunction(int id, IntArray intArray) {
		this.amts = intArray;
		this.id = id;
		this.size = 0;
	}

	public IntArray getAmts() {
		return amts;
	}

	public void addAmtId(int amtId) {
		amts.add(amtId);
	}

	public void addSize() {
		size++;
	}

	public int getId() {
		return id;
	}

	public int getSize() {
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amts == null) ? 0 : amts.hashCode());
		result = prime * result + size;
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
		Conjunction other = (Conjunction) obj;
		if (size != other.size)
			return false;
		if (amts == null) {
			if (other.amts != null)
				return false;
		} else if (!amts.equals(other.amts))
			return false;

		return true;
	}

}
