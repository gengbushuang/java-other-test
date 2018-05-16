package com.dnf.reverse1.index;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.Query;

public class CountryIndex implements Index {

	String country_key = ConstantKey.AD_COUNTRY;

	@Override
	public void createIndex(Audience audience) {
		int country_support_mode = audience.getCountry_support_mode();
		String countrys = audience.getCountrys();
	}

	@Override
	public void queryIndex(Query query) {

	}

	@Override
	public void delIndex(Audience audience) {

	}

}
