package com.dnf.reverse2.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Doc extends ID {

	private List<Integer> conjs;

	public Doc(List<Integer> list) {
		this(list, 0);
	}

	public Doc(List<Integer> list, int id) {
		super(id);
		if (list != null) {
			Collections.sort(list);
		}

		this.conjs = list;
	}

	public List<Integer> getConjs() {
		return conjs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conjs == null) ? 0 : conjs.hashCode());
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
		Doc other = (Doc) obj;
		if (conjs == null) {
			if (other.conjs != null)
				return false;
		} else if (!conjs.equals(other.conjs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(50);
		sb.append("{\"id\":").append(getId()).append(",\"conjs\":[");
		for (int i = 0; i < conjs.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(conjs.get(i));
		}
		sb.append("]}");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		List<Integer> collect = Stream.of(4,7,9).collect(Collectors.toList());
		Doc doc = new Doc(collect, 7);
		System.out.println(doc);
	}
}
