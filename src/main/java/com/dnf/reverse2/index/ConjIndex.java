package com.dnf.reverse2.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		List<Conjunction> conjs = index.conjs;
		
//		int id = Collections.binarySearch(conjs,conjunction,conjunctionComparator);
//		if(id>-1) {
//			
//			System.out.println("conj-->"+id);
//			
//			return conjs.get(id).getId();
//		}
		
		for (Conjunction conj : conjs) {
			if (conj.equals(conjunction)) {
				return conj.getId();
			}
		}
		
		conjunction.setId(conjs.size());
		conjs.add(conjunction);
		
		
//		conjunction.setId(index.conjs.size());
//		index.conjs.add(conjunction);
//		
//		Collections.sort(index.conjs, conjunctionComparator);
		
		index.conjRvs.add(new ArrayList<>(4));

		return conjunction.getId();
	}

}
