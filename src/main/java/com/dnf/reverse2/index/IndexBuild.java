package com.dnf.reverse2.index;

import java.util.ArrayList;
import java.util.List;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Doc;

public class IndexBuild {

	ConjIndex conjIndex = new ConjIndex();

	public void appendIndex(Index index, Audience audience) {
		Doc doc = analysisDoc(index, audience);

		conjReverse1(index, doc);
	}

	/**
	 * 一级倒排
	 * 
	 * @param index
	 */
	public void conjReverse1(Index index, Doc doc) {
		int adid = doc.getId();
		List<Integer> conjs = doc.getConjs();

		for (Integer conjId : conjs) {
			index.conjReverse1(conjId, adid);
		}
	}

	/**
	 * 二级倒排
	 * 
	 * @param index
	 * @param conjunction
	 */
	public void conjReverse2(Index index, Conjunction conjunction) {
		index.conjReverse2(conjunction);
	}

	public Doc analysisDoc(Index index, Audience audience) {

		Conjunction conj = conjIndex.build(audience, index);
		conjReverse2(index, conj);

		List<Integer> integers = new ArrayList<>(1);
		integers.add(conj.getId());
		Doc doc = new Doc(integers, audience.getId());

		index.add(doc);

		return doc;
	}
	
	public void del(Index index, Audience audience) {
		Doc doc = new Doc(null, audience.getId());
		index.del(doc);
	}
}
