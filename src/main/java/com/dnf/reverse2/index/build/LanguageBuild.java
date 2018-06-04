package com.dnf.reverse2.index.build;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.StringUtil;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;

public class LanguageBuild extends AssignBuild {

	@Override
	public Assignment analysisTerm(Audience audience, Index index) {
		String language = audience.getLanguage();

		if (StringUtil.isBlank(language)) {
			return null;
		}

		List<Integer> terms = new ArrayList<>(1);
		
		Integer termId = super.toTermId("language", language, index);
		
		terms.add(termId);

		Assignment assignment = new Assignment(terms, true);

		return assignment;
	}

}
