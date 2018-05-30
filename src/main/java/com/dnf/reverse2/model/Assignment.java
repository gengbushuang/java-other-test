package com.dnf.reverse2.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assignment implements Serializable {
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
		this(list, belong, 0);
	}
	
	public Assignment(List<Integer> list, boolean belong,int id) {
		Collections.sort(list);
		this.terms = list;
		this.belong = belong;
		this.id = id;
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

	public void wirte(DataOutputStream out) throws IOException {
		out.writeInt(id);
		out.writeBoolean(belong);
		out.writeInt(terms.size());
		for (Integer term : terms) {
			out.writeInt(term);
		}
	}

	public void read(DataInputStream in) throws IOException {
		this.id = in.readInt();
		this.belong = in.readBoolean();
		int size = in.readInt();
		List<Integer> tmps = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			tmps.add(in.readInt());
		}
		this.terms = tmps;
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
