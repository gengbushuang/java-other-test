package com.dnf.reverse1.index;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.IndexBuilder;
import com.dnf.reverse1.QueryBuilder;
import com.dnf.reverse1.model.Query;

public class AppIndex implements Index {

	public String fieldName() {
		return "app";
	}

	@Override
	public void createIndex(Audience audience,IndexBuilder indexBuildr) {
		String apps = audience.getApps();
		int app_support_mode = audience.getApp_support_mode();
		// 如果有排除,就创建排除索引
		if (app_support_mode == 0 && StringUtils.isNotBlank(apps)) {
			indexBuildr.eliminate(fieldName(), apps, audience.getId());
		}
		
		if (StringUtils.isBlank(apps) || app_support_mode == 0) {
			indexBuildr.set(ConstantKey.AD_APP + "all", String.valueOf(audience.getId()));
			return;
		}
		
		if (app_support_mode == 1) {
			String[] appArray = StringUtils.splitByWholeSeparator(apps, ",");
			String[] keys = Stream.of(appArray).map(x -> ConstantKey.AD_APP + x).toArray(String[]::new);
			for (String key : keys) {
				indexBuildr.set(key, String.valueOf(audience.getId()));
			}
		}
	}

	@Override
	public void queryIndex(Query query,QueryBuilder queryBuilder) {
		String apps = query.getApps();
		
		String key_apps_all = ConstantKey.AD_APP + "all";
		String key_apps_tmp = ConstantKey.AD_APP + "tmp";

		if (StringUtils.isBlank(apps)) {
			queryBuilder.sunion(key_apps_tmp, key_apps_all);
			return;
		}
		String[] appArray = StringUtils.splitByWholeSeparator(apps + ",all", ",");
		String[] keys = Stream.of(appArray).map(x -> ConstantKey.AD_APP + x).toArray(String[]::new);
		queryBuilder.sunion(key_apps_tmp, keys);
	}

	@Override
	public void delIndex(Audience audience) {

	}


}
