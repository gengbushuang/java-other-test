package com.dnf.reverse2.index.build;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Term;

public abstract class AssignBuild {
	
	public abstract Assignment analysisTerm(Audience audience,Index index);
	
	public Integer toTermId(String key,String value,Index index) {
		Term term = new Term(key, value);
		Integer integer = index.termToInt(term);
		
		return integer;
	}
}
