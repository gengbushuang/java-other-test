package com.dnf.reverse2.set;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class CountSet {
	private int count;

	private Map<Integer, Integer> positive;

	private Map<Integer, Integer> negetive;

	private Map<Integer, Boolean> result;

	public CountSet(int count) {
		this.count = count;
		this.positive = new TreeMap<>();
		this.negetive = new TreeMap<>();
		this.result = new TreeMap<>();
	}

	public void add(int id, boolean post) {
		if (!post) {
			negetive.put(id, 1);
		} else {
			Integer val = positive.get(id);
			if (val == null) {
				val = new Integer(0);
			}
			val += 1;
			if (val.intValue() >= this.count) {
				result.put(id, true);
			} else {
				positive.put(id, val);
			}
		}
	}

	public int[] ToSlice() {
		for (Entry<Integer, Integer> entry : negetive.entrySet()) {
			Boolean b = result.get(entry.getKey());
			if (b != null && b) {
				result.remove(entry.getKey());
			}
		}

		Set<Integer> keySet = result.keySet();

		int[] rc = keySet.stream().mapToInt(x -> x.intValue()).sorted().toArray();
		return rc;
	}
}
