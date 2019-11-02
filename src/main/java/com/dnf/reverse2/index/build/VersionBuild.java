package com.dnf.reverse2.index.build;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;

public class VersionBuild extends AssignBuild {

	@Override
	public Assignment analysisTerm(Audience audience, Index index) {
		String version_ids = audience.getVersion_ids();
		if (StringUtils.isBlank(version_ids)) {
			return null;
		}

		String[] versionArray = StringUtils.splitByWholeSeparator(version_ids, ",");
		List<Integer> terms = new ArrayList<>(versionArray.length);

		for (String version : versionArray) {

			Integer termId = super.toTermId("version", version, index);
			terms.add(termId);
		}

		Assignment assignment = new Assignment(terms, true);

		return assignment;
	}

}
