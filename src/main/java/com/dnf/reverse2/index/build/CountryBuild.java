package com.dnf.reverse2.index.build;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.model.Assignment;

public class CountryBuild extends AssignBuild {

	@Override
	public Assignment analysisTerm(Audience audience, Index index) {

		String countrys = audience.getCountrys();
		if (StringUtils.isBlank(countrys)) {
			return null;
		}
		
		String[] countryArray = StringUtils.splitByWholeSeparator(countrys, ",");
		List<Integer> terms = new ArrayList<>(countryArray.length);

		for (String country : countryArray) {
			Integer termId = super.toTermId("country", country, index);
			terms.add(termId);
		}

		int mode = audience.getCountry_support_mode();

		Assignment assignment = new Assignment(terms, mode == 0 ? false : true);

		return assignment;

	}

}
