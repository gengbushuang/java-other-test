package com.dnf.reverse1.index;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.IndexBuilder;
import com.dnf.reverse1.QueryBuilder;
import com.dnf.reverse1.model.Query;

public class CountryIndex implements Index {

	String country_key = ConstantKey.AD_COUNTRY;

	@Override
	public void createIndex(Audience audience,IndexBuilder indexBuildr) {
		int country_support_mode = audience.getCountry_support_mode();
		String countrys = audience.getCountrys();
	}

	@Override
	public void queryIndex(Query query,QueryBuilder queryBuilder) {

	}

	@Override
	public void delIndex(Audience audience) {

	}

}
