package com.dnf.test;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

public class IndexCreate {

	IndexMode indexMode;

	public IndexCreate(IndexMode indexMode) {
		this.indexMode = indexMode;
	}

	public void createIndex(Audience audience) {
		createApp(audience);
		createLanguage(audience);
		createCountry(audience);
		createVer(audience);
		createPosition(audience);
	}
	
	public void delIndex(Audience audience) {
		
	}

	// app索引
	public void createApp(Audience audience) {
		String apps = audience.getApps();
		int app_support_mode = audience.getApp_support_mode();
		//如果有排除,就创建排除索引
		if (app_support_mode == 0 && StringUtils.isNotBlank(apps)) {
			eliminate("app", apps, audience.getId());
		}
		
		if (StringUtils.isBlank(apps) || app_support_mode == 0) {
			indexMode.setSadd(ConstantKey.AD_APP + "all", String.valueOf(audience.getId()));
			return;
		}
		if (app_support_mode == 1) {
			String[] appArray = StringUtils.splitByWholeSeparator(apps, ",");
			String[] keys = Stream.of(appArray).map(x -> ConstantKey.AD_APP + x).toArray(String[]::new);
			for (String key : keys) {
				indexMode.setSadd(key, String.valueOf(audience.getId()));
			}
		}

	}

	// 语言索引
	public void createLanguage(Audience audience) {
		String language = audience.getLanguage();
		if (StringUtils.isBlank(language)) {
			indexMode.setSadd(ConstantKey.AD_LANGUAGE + "all", String.valueOf(audience.getId()));
		} else {
			indexMode.setSadd(ConstantKey.AD_LANGUAGE + language, String.valueOf(audience.getId()));
		}
	}

	// 国家索引
	public void createCountry(Audience audience) {
		String countrys = audience.getCountrys();
		int country_support_mode = audience.getCountry_support_mode();

		if (country_support_mode == 0 && StringUtils.isNotBlank(countrys)) {
			eliminate("country", countrys, audience.getId());
		}

		if (StringUtils.isBlank(countrys)) {
			indexMode.setSadd(ConstantKey.AD_COUNTRY + "all", String.valueOf(audience.getId()));
			return;
		}
		if (country_support_mode == 1) {
			String[] countryArray = StringUtils.splitByWholeSeparator(countrys, ",");
			String[] keys = Stream.of(countryArray).map(x -> ConstantKey.AD_COUNTRY + x).toArray(String[]::new);
			for (String key : keys) {
				indexMode.setSadd(key, String.valueOf(audience.getId()));
			}
		}

	}

	// 版本索引
	public void createVer(Audience audience) {
		String os_version = audience.getOs_version();
		if (StringUtils.isBlank(os_version)) {
			indexMode.setSadd(ConstantKey.AD_VERSION + "all", String.valueOf(audience.getId()));
		} else {
			String[] versions = os_version.split(",");
			String[] keys = Stream.of(versions).map(x -> ConstantKey.AD_VERSION + x).toArray(String[]::new);
			for (String ver : keys) {
				indexMode.setSadd(ver, String.valueOf(audience.getId()));
			}
		}
	}

	public void createPosition(Audience audience) {
		String positionId = audience.getPositionId();
		indexMode.setSadd(ConstantKey.AD_POSITION + positionId, String.valueOf(audience.getId()));
	}

	private void eliminate(String idenf, String apps, int id) {
		indexMode.eliminate(idenf, apps, id);
	}

	public static void main(String[] args) {
		AppSupportMode app = new AppSupportMode();
		Country country = new Country();
		Position position = new Position();
		Version version = new Version();
		Language language = new Language();
		
		IndexMode indexMode = new IndexMode();

		IndexCreate indexCreate = new IndexCreate(indexMode);
		Audience audience = new Audience();
		audience.setPositionId(position.getV());
		audience.setCountrys(country.getV());
		audience.setCountry_support_mode(1);

		audience.setLanguage(language.getV());
		audience.setOs_version(version.getV());

		audience.setApps(app.getV()+","+app.getV()+","+app.getV());
		audience.setApp_support_mode(1);
		audience.setId(10);
		indexCreate.createIndex(audience);

		indexMode.wirteEliminateIndex("d:/index.tl");

	}
}
