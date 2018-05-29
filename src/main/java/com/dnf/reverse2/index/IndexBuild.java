package com.dnf.reverse2.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.comparator.PairComparator;
import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Pair;

public class IndexBuild {

	ConjIndex conjIndex = new ConjIndex();

	public void appendIndex(Index index, Audience audience) {
		Conjunction conjunction = analysisConjunction(index, audience);

		conjReverse1(index, conjunction, audience);

		conjReverse2(index, conjunction);
	}

	/**
	 * 一级倒排
	 * 
	 * @param index
	 */
	public void conjReverse1(Index index, Conjunction conjunction, Audience audience) {

		int adid = audience.getId();

		int rvsLen = index.conjRvs.size();

		if (rvsLen <= conjunction.getId()) {
			// TODO 这个地方要日志异常
		}

		List<Integer> rvsDocList = index.conjRvs.get(conjunction.getId());
		Collections.sort(rvsDocList);
		int post = Collections.binarySearch(rvsDocList, adid);

		if (post > 0 && post < rvsDocList.size() && rvsDocList.get(post) == adid) {
			return;
		}

		rvsDocList.add(adid);
	}

	/**
	 * 二级倒排
	 * 
	 * @param index
	 * @param conjunction
	 */
	public void conjReverse2(Index index, Conjunction conjunction) {

		int size = conjunction.getSize();
		if (size >= index.conjSzRvs.size()) {
			resizeConjSzRvs(size + 1, index);
		}

		ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> termRvsList = index.conjSzRvs.get(size);
		if (termRvsList == null) {
			termRvsList = new ConcurrentSkipListMap<>();
		}

		List<Integer> assigns = conjunction.getAssigns();
		for (Integer assignId : assigns) {
			insertTermRvsList(conjunction.getId(), assignId, termRvsList, index);
		}

		if (size == 0) {
			insertTermRvsListEmptySet(conjunction.getId(), index);
		}
	}

	private void resizeConjSzRvs(int size, Index index) {
		List<ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>>> conjSzRvs = index.conjSzRvs;
		for (int i = conjSzRvs.size(); i < size; i++) {
			conjSzRvs.add(new ConcurrentSkipListMap<>());
		}
	}

	private void insertTermRvsListEmptySet(int conjId, Index index) {
		ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> termrvslist = index.conjSzRvs.get(0);

		List<Pair<Integer, Boolean>> list = termrvslist.get(-1);
		if(list == null) {
			list = new ArrayList<>();
			termrvslist.put(-1, list);
		}

		insertClist(conjId, true, list);
	}

	private void insertTermRvsList(int conjId, Integer assignId,
			ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> termRvsList, Index index) {
		
		Assignment assignment = index.assigns.get(assignId);

		List<Integer> terms = assignment.getTerms();

		for (Integer termId : terms) {
			List<Pair<Integer, Boolean>> list = termRvsList.get(termId);
			if (list == null) {
				list = new ArrayList<>();
				termRvsList.put(termId, list);
			}
			insertClist(conjId, assignment.isBelong(), list);
		}
	}

	private void insertClist(int conjId, boolean belong, List<Pair<Integer, Boolean>> list) {
		Pair<Integer, Boolean> patr = new Pair<Integer, Boolean>(conjId, belong);
		int idx = Collections.binarySearch(list, patr, pairComparator);
		if (idx > -1) {
			return;
		}
		list.add(patr);
		Collections.sort(list, pairComparator);
	}

	public Conjunction analysisConjunction(Index index, Audience audience) {
		return conjIndex.build(audience, index);
	}

	static PairComparator pairComparator = new PairComparator();
}
