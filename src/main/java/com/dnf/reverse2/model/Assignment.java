package com.dnf.reverse2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assignment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3541613359742440622L;

	private int id;

	private boolean belong;

	private List<Integer> terms;

	public Assignment(boolean belong) {
		this(new ArrayList<>(4), belong);
	}

	public Assignment(List<Integer> list, boolean belong) {
		Collections.sort(list);
		this.terms = list;
		this.belong = belong;
	}

	public boolean isBelong() {
		return belong;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getTerms() {
		return terms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (belong ? 1231 : 1237);
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
		if (belong != other.belong)
			return false;
		if (terms == null) {
			if (other.terms != null)
				return false;
		} else if (!terms.equals(other.terms))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Assignment [id=" + id + ", belong=" + belong + ", terms=" + terms + "]";
	}
}
