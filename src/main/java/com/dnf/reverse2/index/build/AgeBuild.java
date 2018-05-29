package com.dnf.reverse2.index.build;

import java.util.ArrayList;
import java.util.List;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;

public class AgeBuild extends AssignBuild {

	@Override
	public Assignment analysisTerm(Audience audience, Index index) {

		int age_range = audience.getAge_range();
		if (age_range == 0) {
			return null;
		}

		List<Integer> ages = new ArrayList<>();

		while (age_range != 0) {
			int tmp = age_range % 10;
			ages.add(tmp);
			age_range = age_range / 10;
		}

		List<Integer> terms = new ArrayList<>(ages.size());
		for (Integer a : ages) {
			Integer termId = super.toTermId("age", String.valueOf(a), index);
			terms.add(termId);
		}

		Assignment assignment = new Assignment(terms, true);
		return assignment;
	}

}
