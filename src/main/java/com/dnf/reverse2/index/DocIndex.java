package com.dnf.reverse2.index;

import java.util.ArrayList;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Doc;

public class DocIndex extends AbsIndex<Doc>{
	ConjIndex conjIndex = new ConjIndex();
	@Override
	protected Doc build(Audience audience, Index index) {
		Doc doc = new Doc(new ArrayList<>(2));
		Conjunction conjunction = conjIndex.build(audience, index);
		
		
		return null;
	}
	
	private Integer toDocId(Doc doc, Index index) {
		
	}

}
