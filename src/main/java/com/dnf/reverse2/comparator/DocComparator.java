package com.dnf.reverse2.comparator;

import java.util.Comparator;

import com.dnf.reverse2.model.Doc;

public class DocComparator implements Comparator<Doc>{

	@Override
	public int compare(Doc o1, Doc o2) {
		return o1.getId()-o2.getId();
	}

}
