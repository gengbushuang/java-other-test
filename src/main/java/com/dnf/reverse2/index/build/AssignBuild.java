package com.dnf.reverse2.index.build;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Term;

public abstract class AssignBuild {
	
	public abstract Assignment analysisTerm(Audience audience,Index index);
	
	public Integer toTermId(String key,String value,Index index) {
		String key_value = key+"&"+value;
		Integer integer = index.termMap.get(key_value);
		if (integer == null) {
			integer = new Integer(index.terms.size());
			index.termMap.put(key_value, integer);
			index.terms.add(new Term(integer, key, value));
		}
		
		return integer;
	}
}
