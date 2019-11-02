package com.dnf.reverse1.index;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.IndexBuilder;
import com.dnf.reverse1.QueryBuilder;
import com.dnf.reverse1.model.Query;

/**
 * 版本索引
 * 
 * @author gengbushuang
 *
 */
public class VersionIndex implements Index {

	@Override
	public void createIndex(Audience audience, IndexBuilder indexBuildr) {
		String os_version = audience.getOs_version();
		String id = String.valueOf(audience.getId());
		if (StringUtils.isBlank(os_version)) {
			indexBuildr.set(ConstantKey.AD_VERSION + "all", String.valueOf(audience.getId()));
			// indexBuildr.zset(ConstantKey.AD_VERSION + "all", audience.getId(), id);
			indexBuildr.positiveRow(id, ConstantKey.AD_VERSION + "all");
		} else {
			String[] versions = os_version.split(",");
			String[] keys = Stream.of(versions).map(x -> ConstantKey.AD_VERSION + x).toArray(String[]::new);
			for (String ver : keys) {
				indexBuildr.set(ver, String.valueOf(audience.getId()));
				// indexBuildr.zset(ver, audience.getId(), id);
				indexBuildr.positiveRow(id, ver);
			}
		}
	}

	@Override
	public void queryIndex(Query query, QueryBuilder queryBuilder) {
		String ver = query.getVer();
		String key_ver_all = ConstantKey.AD_VERSION + "all";
		String key_ver_tmp = ConstantKey.AD_VERSION + "tmp";
		if (StringUtils.isBlank(ver) || ver.equals("all")) {
			queryBuilder.sunion(key_ver_tmp, key_ver_all);
			return;
		}

		String key_ver = ConstantKey.AD_VERSION + ver;
		// 并集查询
		queryBuilder.sunion(key_ver_tmp, key_ver, key_ver_all);
	}

	// 先注释掉,以后删除
	// @Override
	// public void delIndex(Audience audience, DelBuilder delBuilder) {
	// String os_version = audience.getOs_version();
	// if (StringUtils.isBlank(os_version)) {
	// delBuilder.del(ConstantKey.AD_VERSION + "all",
	// String.valueOf(audience.getId()));
	// } else {
	// String[] versions = os_version.split(",");
	// String[] keys = Stream.of(versions).map(x -> ConstantKey.AD_VERSION +
	// x).toArray(String[]::new);
	// for (String ver : keys) {
	// delBuilder.del(ver, String.valueOf(audience.getId()));
	// }
	// }
	//
	// }

}
