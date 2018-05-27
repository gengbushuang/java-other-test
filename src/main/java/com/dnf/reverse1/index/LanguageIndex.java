package com.dnf.reverse1.index;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.DelBuilder;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.IndexBuilder;
import com.dnf.reverse1.QueryBuilder;
import com.dnf.reverse1.model.Query;

public class LanguageIndex implements Index {

	public String fieldName() {
		return "la";
	}

	@Override
	public void createIndex(Audience audience, IndexBuilder indexBuildr) {
		String language = audience.getLanguage();
		String id = String.valueOf(audience.getId());
		if (StringUtils.isBlank(language)) {
//			indexBuildr.set(ConstantKey.AD_LANGUAGE + "all", String.valueOf(audience.getId()));
			indexBuildr.zset(ConstantKey.AD_LANGUAGE + "all", audience.getId(), id);
			indexBuildr.set(id, ConstantKey.AD_LANGUAGE + "all");
		} else {
//			indexBuildr.set(ConstantKey.AD_LANGUAGE + language, String.valueOf(audience.getId()));
			indexBuildr.zset(ConstantKey.AD_LANGUAGE + language, audience.getId(), id);
			indexBuildr.set(id, ConstantKey.AD_LANGUAGE + language);
		}
	}

	@Override
	public void queryIndex(Query query, QueryBuilder queryBuilder) {
		String language = query.getLanguage();
		String key_language_all = ConstantKey.AD_LANGUAGE + "all";
		String key_language_tmp = ConstantKey.AD_LANGUAGE + "tmp";
		if (StringUtils.isBlank(language) || language.equals("all")) {
			queryBuilder.sunion(fieldName(), key_language_tmp, key_language_all);
			return;
		}
		String key_language = ConstantKey.AD_LANGUAGE + language;
		queryBuilder.sunion(fieldName(), key_language_tmp, key_language, key_language_all);
	}

	@Override
	public void delIndex(Audience audience, DelBuilder delBuilder) {
		String language = audience.getLanguage();
		if (StringUtils.isBlank(language)) {
			delBuilder.del(ConstantKey.AD_LANGUAGE + "all", String.valueOf(audience.getId()));
		} else {
			delBuilder.del(ConstantKey.AD_LANGUAGE + language, String.valueOf(audience.getId()));
		}
	}

}
