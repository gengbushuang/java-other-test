package com.dnf.reverse2.index.build;

import java.util.ArrayList;
import java.util.List;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;

public class SexBuild extends AssignBuild {

	@Override
	public Assignment analysisTerm(Audience audience, Index index) {
		int gender = audience.getGender();
		if (gender == 0) {
			return null;
		}

		List<Integer> terms = new ArrayList<>(1);

		Integer termId = super.toTermId("sex", String.valueOf(gender), index);
		terms.add(termId);

		Assignment assignment = new Assignment(terms, true);

		return assignment;
	}

}
