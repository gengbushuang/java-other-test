package com.dnf.test.model;

import com.dnf.test.struct.ObjectArray;

public class TermRvs {
	private int termId;
	private ObjectArray<CPair> cList;

	public TermRvs(int termId, ObjectArray<CPair> clist) {
		this.termId = termId;
		this.cList = clist;
	}

	
}
