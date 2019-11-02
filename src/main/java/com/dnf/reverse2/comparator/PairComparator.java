package com.dnf.reverse2.comparator;

import java.util.Comparator;

import com.dnf.reverse2.model.Pair;

public class PairComparator implements Comparator<Pair<Integer, Boolean>> {

	@Override
	public int compare(Pair<Integer, Boolean> o1, Pair<Integer, Boolean> o2) {
		return o1._1()-o2._1();
	}

}
