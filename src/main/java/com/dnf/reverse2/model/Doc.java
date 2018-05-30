package com.dnf.reverse2.model;

import java.util.List;

public class Doc {
	private int id;
	private List<Integer> conjs;

	public Doc(List<Integer> list) {
		this(list, 0);
	}

	public Doc(List<Integer> list, int id) {
		this.id = id;
		this.conjs = list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getConjs() {
		return conjs;
	}
}
