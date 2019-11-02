package com.dnf.reverse2.comparator;

import java.util.Comparator;

import com.dnf.reverse2.model.Conjunction;

public class ConjunctionComparator implements Comparator<Conjunction>{
	
	@Override
	public int compare(Conjunction o1, Conjunction o2) {
		return o1.getId()-o2.getId();
	}
	
	
}
