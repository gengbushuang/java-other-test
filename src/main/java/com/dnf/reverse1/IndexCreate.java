package com.dnf.reverse1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.dnf.model.Audience;
import com.dnf.reverse1.model.Query;
import com.dnf.test.ReidsDb;

public class IndexCreate {

	Map<String, Map<String, BitSet>> map = null;

	Jedis jedis = ReidsDb.DB().getJedis();

	public List<Index> indexs = new ArrayList<Index>();

	public void createIndex(Audience audience) {
		IndexMode indexMode = new IndexMode(jedis, map);
		for (Index index : indexs) {
			index.createIndex(audience, indexMode);
		}
		indexMode.execute();
	}

	public void queryIndex(Query query) {
		QueryMode queryMode = new QueryMode(jedis);
		for (Index index : indexs) {
			index.queryIndex(query, queryMode);
		}

		List<Object> resule = queryMode.execute();

		// 查询包含
		BitSet result = query(query, resule);
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

		Object object = resule.get(resule.size() - 2);

	}

	class IndexMode implements IndexBuilder {
		Pipeline pipelined;
		Map<String, Map<String, BitSet>> map;

		public IndexMode(Jedis jedis, Map<String, Map<String, BitSet>> map) {
			this.pipelined = jedis.pipelined();
			this.map = map;
		}

		public void execute() {
			pipelined.sync();
		}

		@Override
		public void eliminate(String idenf, String value, int id) {
			String[] appArray = StringUtils.splitByWholeSeparator(value, ",");
			eliminate(idenf, appArray, id);
		}

		private void eliminate(String idenf, String[] values, int id) {
			Map<String, BitSet> mapTmp = map.get(idenf);
			if (mapTmp == null) {
				mapTmp = new TreeMap<>();
				map.put(idenf, mapTmp);
			}
			for (String app : values) {
				BitSet bitSet = mapTmp.get(app);
				if (bitSet == null) {
					bitSet = new BitSet();
					mapTmp.put(app, bitSet);
				}
				bitSet.set(id);
			}
		}

		@Override
		public void set(String key, String... member) {
			setSadd(key, member);
		}

		private void setSadd(String key, String... member) {
			pipelined.sadd(key, member);
		}

	}

	class QueryMode implements QueryBuilder {
		Pipeline pipelined;

		public QueryMode(Jedis jedis) {
			this.pipelined = jedis.pipelined();
		}

		@Override
		public void sunion(String dstkey, String... keys) {
			sunionstore(dstkey, keys);
		}

		private void sunionstore(String dstkey, String... keys) {
			pipelined.sunionstore(dstkey, keys);
		}

		public List<Object> execute() {
			return pipelined.syncAndReturnAll();
		}
	}
}
