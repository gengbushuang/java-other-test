package com.dnf.reverse2.query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import com.dnf.Cond;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Pair;
import com.dnf.reverse2.set.CountSet;
import com.dnf.reverse2.set.IntSet;

public class BoolQuery {

	public int[] query(Cond[] conds, Index index) {

		List<Integer> terms = new ArrayList<>(conds.length);

		for (Cond cond : conds) {
			Integer termId = index.queryToTermId(cond.getKey(), cond.getVal());
			if (termId != null) {
				terms.add(termId);
			}
		}

		if (terms.isEmpty()) {
			return new int[0];
		}

		int[] array = new int[terms.size()];
		for (int i = 0; i < terms.size(); i++) {
			array[i] = terms.get(i).intValue();
		}
		return Search(array, index);
	}

	public int[] Search(int[] terms, Index index) {
		int[] conjs = getConjs(terms, index);
		if (conjs == null || conjs.length == 0) {
			return new int[0];
		}
		return getAdId(conjs, index);
	}

	private int[] getAdId(int[] conjs, Index index) {
		IntSet intSet = new IntSet();
		ConcurrentSkipListMap<Integer, List<Integer>> conjRvs = index.getConjRvs();
		int size = conjRvs.keySet().size();

		for (int i = 0; i < conjs.length; i++) {
			if (conjs[i] >= size) {
				continue;
			}
			List<Integer> doclist = conjRvs.get(conjs[i]);
			if (doclist == null || doclist.isEmpty()) {
				continue;
			}
			for (int doc : doclist) {
				intSet.Add(doc);
			}
		}
		return intSet.ToSlice();
	}

	private int[] getConjs(int[] terms, Index index) {
		int n = terms.length;

		List<ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>>> conjSzRvs = index.getConjSzRvs();

		int size = conjSzRvs.size();

		if (size < 0) {
			System.exit(1);
		}

		if (n >= size) {
			n = size - 1;
		}

		IntSet intSet = new IntSet();

		for (int i = 0; i <= n; i++) {
			ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> termlist = conjSzRvs.get(i);

			if (termlist == null || termlist.isEmpty()) {
				continue;
			}
			CountSet countSet = new CountSet(i);
			for (int termId : terms) {
				List<Pair<Integer, Boolean>> LPair = termlist.get(termId);
				if (LPair == null) {
					continue;
				}
				for (Pair<Integer, Boolean> pair : LPair) {
					countSet.add(pair._1(), pair._2());
				}
			}

			if (i == 0) {
				List<Pair<Integer, Boolean>> LPair = termlist.get(-1);
				if (LPair == null) {
					continue;
				}
				for (Pair<Integer, Boolean> pair : LPair) {
					countSet.add(pair._1(), pair._2());
				}
			}
			intSet.AddSlice(countSet.ToSlice());
		}
		return intSet.ToSlice();
	}
}
