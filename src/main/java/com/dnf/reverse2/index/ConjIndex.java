package com.dnf.reverse2.index;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.comparator.ConjunctionComparator;
import com.dnf.reverse2.model.Conjunction;

public class ConjIndex extends AbsIndex<Conjunction> {

	static ConjunctionComparator conjunctionComparator = new ConjunctionComparator();
	
	AssignIndex assignIndex = new AssignIndex();

	@Override
	protected Conjunction build(Audience audience,Index index) {

		Conjunction conjunction = assignIndex.build(audience,index);
		Integer conjId = toConjId(conjunction, index);

		conjunction.setId(conjId);

		return conjunction;
	}

	private Integer toConjId(Conjunction conjunction, Index index) {
		
		Integer integer = index.conjToInt(conjunction);
		return integer;
	}

}
