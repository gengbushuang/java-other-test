package com.dnf.reverse2.index.build;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;

public class AppBuild extends AssignBuild {

	public Assignment analysisTerm(Audience audience, Index index) {

		String apps = audience.getApps();
		if (StringUtils.isBlank(apps)) {
			return null;
		}

		String[] appArray = StringUtils.splitByWholeSeparator(apps, ",");
		List<Integer> terms = new ArrayList<>(appArray.length);

		for (String app : appArray) {
			Integer termId = super.toTermId("app", app, index);
			terms.add(termId);
		}
		
		int mode = audience.getApp_support_mode();

		Assignment assignment = new Assignment(terms, mode == 0 ? false : true);

		return assignment;
	}
}
