package com.dnf.reverse2.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conjunction extends ID {

	private int size;

	private List<Integer> assigns;

	public Conjunction(List<Integer> list, int size) {
		this(list, size, 0);
	}

	public Conjunction(List<Integer> list, int size, int id) {
		super(id);
		if (list != null) {
			Collections.sort(list);
		}

		this.assigns = list;
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public List<Integer> getAssigns() {
		return assigns;
	}

	public void wirte(DataOutputStream out) throws IOException {
		out.writeInt(this.getId());
		out.writeInt(size);
		out.writeInt(assigns.size());
		for (Integer assign : assigns) {
			out.writeInt(assign);
		}
	}

	public void read(DataInputStream in) throws IOException {
		this.setId(in.readInt());
		this.size = in.readInt();
		int size = in.readInt();
		List<Integer> tmps = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			tmps.add(in.readInt());
		}
		this.assigns = tmps;
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
		StringBuilder sb = new StringBuilder(50);
		sb.append("{\"id\":").append(getId()).append(",\"terms\":[");
		for (int i = 0; i < assigns.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(assigns.get(i));
		}
		sb.append("]");
		return sb.toString();
	}
}
