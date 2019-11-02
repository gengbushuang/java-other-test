package com.dnf.reverse2.comparator;

import java.util.Comparator;

import com.dnf.reverse2.model.Assignment;

public class AssignmentComparator implements Comparator<Assignment>{
	@Override
	public int compare(Assignment o1, Assignment o2) {
		return o1.getId()-o2.getId();
	}
}
