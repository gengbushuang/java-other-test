package com.dnf.reverse1.index;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.DelBuilder;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.IndexBuilder;
import com.dnf.reverse1.QueryBuilder;
import com.dnf.reverse1.model.Query;

public class CountryIndex implements Index {

	public String fieldName() {
		return "cy";
	}

	@Override
	public void createIndex(Audience audience, IndexBuilder indexBuildr) {
		String countrys = audience.getCountrys();
		int country_support_mode = audience.getCountry_support_mode();
		String id = String.valueOf(audience.getId());
		if (country_support_mode == 0 && StringUtils.isNotBlank(countrys)) {
			indexBuildr.eliminate(fieldName(), countrys, audience.getId());
		}

		if (StringUtils.isBlank(countrys)) {
			//indexBuildr.set(ConstantKey.AD_COUNTRY + "all", String.valueOf(audience.getId()));
			indexBuildr.zset(ConstantKey.AD_COUNTRY + "all", audience.getId(), id);
			indexBuildr.set(id, ConstantKey.AD_COUNTRY + "all");
			return;
		}
		if (country_support_mode == 1) {
			String[] countryArray = StringUtils.splitByWholeSeparator(countrys, ",");
			String[] keys = Stream.of(countryArray).map(x -> ConstantKey.AD_COUNTRY + x).toArray(String[]::new);
			for (String key : keys) {
				//indexBuildr.set(key, String.valueOf(audience.getId()));
				indexBuildr.zset(key, audience.getId(), id);
				//
				indexBuildr.set(id, key);
			}
		}

	}

	@Override
	public void queryIndex(Query query, QueryBuilder queryBuilder) {
		String country = query.getCountry();
		String key_country_all = ConstantKey.AD_COUNTRY + "all";
		String key_country_tmp = ConstantKey.AD_COUNTRY + "tmp";
		if (StringUtils.isBlank(country) || country.equals("all")) {
			queryBuilder.sunion(fieldName(),key_country_tmp, key_country_all);
			return;
		}
		
		String key_country = ConstantKey.AD_COUNTRY + country;
		queryBuilder.sunion(fieldName(),key_country_tmp, key_country, key_country_all);
	}

	@Override
	public void delIndex(Audience audience, DelBuilder delBuilder) {
		String countrys = audience.getCountrys();
		int country_support_mode = audience.getCountry_support_mode();

		if (country_support_mode == 0 && StringUtils.isNotBlank(countrys)) {
			delBuilder.delEliminate(fieldName(), countrys, audience.getId());
		}

		if (StringUtils.isBlank(countrys)) {
			delBuilder.del(ConstantKey.AD_COUNTRY + "all", String.valueOf(audience.getId()));
			return;
		}
		if (country_support_mode == 1) {
			String[] countryArray = StringUtils.splitByWholeSeparator(countrys, ",");
			String[] keys = Stream.of(countryArray).map(x -> ConstantKey.AD_COUNTRY + x).toArray(String[]::new);
			for (String key : keys) {
				delBuilder.del(key, String.valueOf(audience.getId()));
			}
		}
	}

}
