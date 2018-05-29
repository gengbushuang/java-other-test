package com.dnf.reverse2.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Conjunction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7855402431160915525L;

	private int id;

	private int size;

	private List<Integer> assigns;

	public Conjunction(List<Integer> list, int size) {
		Collections.sort(list);
		this.assigns = list;
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public List<Integer> getAssigns() {
		return assigns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assigns == null) ? 0 : assigns.hashCode());
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
		if (assigns == null) {
			if (other.assigns != null)
				return false;
		} else if (!assigns.equals(other.assigns))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Conjunction [id=" + id + ", size=" + size + ", assigns=" + assigns + "]";
	}
}
