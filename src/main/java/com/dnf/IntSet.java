package com.dnf;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class IntSet {
	Map<Integer, Boolean> data;

	public IntSet() {
		data = new TreeMap<>();
	}

	public void AddSlice(int[] elems) {
		for (int elem : elems) {
			data.put(elem, true);
		}
	}

	public void Add(int elem) {
		data.put(elem, true);
	}

	public int[] ToSlice() {
		Set<Integer> keySet = data.keySet();
		int[] rc = keySet.stream().mapToInt(x -> x.intValue()).sorted().toArray();
		return rc;
	}
}
