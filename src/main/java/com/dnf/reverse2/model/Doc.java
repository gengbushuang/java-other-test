package com.dnf.reverse2.model;

import java.util.Collections;
import java.util.List;

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
}
