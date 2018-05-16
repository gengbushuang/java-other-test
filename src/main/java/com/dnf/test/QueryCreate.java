package com.dnf.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.ConstantKey;
import com.dnf.reverse1.model.Query;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class QueryCreate {

	IndexMode indexMode;

	QueryParam param = new QueryParam();

	public QueryCreate(IndexMode indexMode) {
		this.indexMode = indexMode;
	}

	public void query(Query query) {
		// System.out.println("查询包含");
		// 查询包含
		BitSet result = query(query, param);
		if (result.isEmpty()) {
			return;
		}
		// System.out.println("查询排除");
		// 查询排除
		long n = System.currentTimeMillis();
		BitSet eliminate = queryEliminate(query);
		// System.out.println("过滤排除");
		// 过滤排除
		result.andNot(eliminate);
		System.out.println(System.currentTimeMillis() - n);

		int[] index = result.stream().toArray();
		
		System.out.println(Arrays.toString(index));
		// for (int i = result.nextSetBit(0); i >= 0; i = result.nextSetBit(i + 1)) {
		// System.out.print(i + "\t");
		// }

	}

	private BitSet query(Query query, QueryParam param) {
		BitSet bitSet = new BitSet();
		List<Object> requests = indexMode.query(query, param);
		for (Object o : requests) {
			if (o instanceof HashSet) {
				HashSet new_name = (HashSet) o;
				if (!new_name.isEmpty()) {
					for (Object object : new_name) {
						bitSet.set(Integer.parseInt(object.toString()));
					}
				}
				break;
			}
		}
		return bitSet;
	}

	private BitSet queryEliminate(Query query) {
		BitSet bitSet = new BitSet();
		queryCountryEliminate(query.getCountry(), bitSet);
		queryAppEliminate(query.getApps(), bitSet);
		queryLanguageEliminate(query.getLanguage(), bitSet);
		queryVerEliminate(query.getVer(), bitSet);
		return bitSet;
	}

	private void queryCountryEliminate(String country, BitSet bitSet) {
		String[] coutrys = StringUtils.splitByWholeSeparator(country, ",");
		indexMode.queryEliminate("country", coutrys, bitSet);
	}

	private void queryAppEliminate(String app, BitSet bitSet) {
		String[] apps = StringUtils.splitByWholeSeparator(app, ",");
		indexMode.queryEliminate("app", apps, bitSet);
	}

	private void queryLanguageEliminate(String language, BitSet bitSet) {
		indexMode.queryEliminate("la", language, bitSet);
	}

	private void queryVerEliminate(String ver, BitSet bitSet) {
		indexMode.queryEliminate("ver", ver, bitSet);
	}

	private class QueryParam implements QueryCall {

		@Override
		public List<Object> query(Query query, Jedis jedis) {
			Pipeline pipelined = jedis.pipelined();
			pipelined.select(1);

			List<String> keys = new ArrayList<String>();

			queryCountry(query, pipelined, keys);
			queryLanguage(query, pipelined, keys);
			queryVer(query, pipelined, keys);
			queryApp(query, pipelined, keys);

			String getpId = query.getpId();
			String key_position = ConstantKey.AD_POSITION + getpId;

			String[] keyArray = keys.toArray(new String[0]);

			keys.add(key_position);
			pipelined.sinter(keys.toArray(new String[0]));
			pipelined.del(keyArray);

			return pipelined.syncAndReturnAll();
		}

	}

	// 国家查询
	public void queryCountry(Query query, Pipeline pipelined, List<String> list) {
		String country = query.getCountry();
		String key_country_all = ConstantKey.AD_COUNTRY + "all";
		String key_country_tmp = ConstantKey.AD_COUNTRY + "tmp";
		if (StringUtils.isBlank(country) || country.equals("all")) {
			pipelined.sunionstore(key_country_tmp, key_country_all);
			list.add(key_country_tmp);
			return;
		}

		String key_country = ConstantKey.AD_COUNTRY + country;
		pipelined.sunionstore(key_country_tmp, key_country, key_country_all);
		list.add(key_country_tmp);
	}

	/**
	 * 查询语言
	 * 
	 * @param query
	 * @param pipelined
	 * @param list
	 */
	public void queryLanguage(Query query, Pipeline pipelined, List<String> list) {
		String language = query.getLanguage();
		String key_language_all = ConstantKey.AD_LANGUAGE + "all";
		String key_language_tmp = ConstantKey.AD_LANGUAGE + "tmp";
		if (StringUtils.isBlank(language) || language.equals("all")) {
			pipelined.sunionstore(key_language_tmp, key_language_all);
			list.add(key_language_tmp);
			return;
		}
		String key_language = ConstantKey.AD_LANGUAGE + language;
		pipelined.sunionstore(key_language_tmp, key_language, key_language_all);
		list.add(key_language_tmp);
	}

	/**
	 * 版本
	 * 
	 * @param query
	 * @param pipelined
	 * @param list
	 */
	public void queryVer(Query query, Pipeline pipelined, List<String> list) {
		String ver = query.getVer();
		String key_ver_all = ConstantKey.AD_VERSION + "all";
		String key_ver_tmp = ConstantKey.AD_VERSION + "tmp";
		if (StringUtils.isBlank(ver) || ver.equals("all")) {
			pipelined.sunionstore(key_ver_tmp, key_ver_all);
			list.add(key_ver_tmp);
			return;
		}

		String key_ver = ConstantKey.AD_VERSION + ver;
		pipelined.sunionstore(key_ver_tmp, key_ver, key_ver_all);
		list.add(key_ver_tmp);
	}

	/**
	 * app查询
	 * 
	 * @param query
	 * @param pipelined
	 * @param list
	 */
	public void queryApp(Query query, Pipeline pipelined, List<String> list) {
		String apps = query.getApps();

		String key_apps_all = ConstantKey.AD_APP + "all";
		String key_apps_tmp = ConstantKey.AD_APP + "tmp";

		if (StringUtils.isBlank(apps)) {
			pipelined.sunionstore(key_apps_tmp, key_apps_all);
			list.add(key_apps_tmp);
			return;
		}
		String[] appArray = StringUtils.splitByWholeSeparator(apps + ",all", ",");
		String[] keys = Stream.of(appArray).map(x -> ConstantKey.AD_APP + x).toArray(String[]::new);

		pipelined.sunionstore(key_apps_tmp, keys);
		list.add(key_apps_tmp);
	}
}
