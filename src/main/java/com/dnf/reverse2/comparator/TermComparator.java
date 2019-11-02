package com.dnf.reverse2.comparator;

import java.util.Comparator;

import com.dnf.reverse2.model.Term;

public class TermComparator implements Comparator<Term>{

	@Override
	public int compare(Term o1, Term o2) {
		return o1.getId()-o2.getId();
	}

}
