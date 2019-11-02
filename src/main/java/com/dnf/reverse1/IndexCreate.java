package com.dnf.reverse1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.model.Query;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class IndexCreate {

	Map<String, Map<String, BitSet>> map = null;

	// Jedis jedis = ReidsDb.DB().getJedis();

	public List<Index> indexs = new ArrayList<Index>();

	public IndexCreate() {
		readEliminateIndex("D:/index.tl");
		if (map == null) {
			map = new HashMap<String, Map<String, BitSet>>();
		}
	}

	/**
	 * 创建索引
	 * 
	 * @param audience
	 */
	public void createIndex(Audience audience) {
		try (Jedis jedis = ReidsDb.DB().getJedis();) {
			IndexMode indexMode = new IndexMode(jedis, map);
			for (Index index : indexs) {
				index.createIndex(audience, indexMode);
			}
			indexMode.execute();
		}
	}

	/**
	 * 查询索引
	 * 
	 * @param query
	 */
	public void queryIndex(Query query) {
		BitSet result = query(query, indexs);

		int[] index = result.stream().toArray();

		System.out.println(Arrays.toString(index));
	}

	/**
	 * 删除索引
	 * 
	 * @param audience
	 */
	public void delIndex(Audience audience) {
		int id = audience.getId();
		String ids = String.valueOf(id);
		// 构建排除集会
		List<String> eliminates = new ArrayList<>();
		try (Jedis jedis = ReidsDb.DB().getJedis();) {
			// 获取某个广告倒排字典表的集合
			Set<String> smembers = jedis.smembers(ConstantKey.AD_ID + ids);
			Pipeline pipelined = jedis.pipelined();
			// 删除倒排字典表对应的广告
			for (String members : smembers) {
				if (members.startsWith("-")) {
					eliminates.add(members.substring(1, members.length()));
					continue;
				}
				pipelined.zrem(members, ids);
			}
			if (eliminates.isEmpty()) {
				return;
			}
			// 删除有排除广告
			for (String eliminate : eliminates) {
				String[] eliminateArray = eliminate.split(":");
				if (eliminateArray.length != 2) {
					continue;
				}
				Map<String, BitSet> mapTmp = map.get(eliminateArray[0]);
				if (mapTmp == null) {
					continue;
				}
				BitSet bitSet = mapTmp.get(eliminateArray[1]);
				if (bitSet == null) {
					continue;
				}
				bitSet.set(id, false);
			}

			// 删除这个广告的倒排字典表集合
			pipelined.del(ConstantKey.AD_ID + ids);
			pipelined.sync();
		}
	}

	// 临时的
	public void wirteEliminateIndex(String path) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(path)))) {
			outputStream.writeObject(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 临时的
	public void readEliminateIndex(String path) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(path)))) {
			Object readObject = inputStream.readObject();
			if (readObject != null) {
				map = (Map<String, Map<String, BitSet>>) readObject;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private BitSet query(Query query, List<Index> indexs) {
		AsyncContex contex;
		try (Jedis jedis = ReidsDb.DB().getJedis();) {
			QueryMode queryMode = new QueryMode("" + System.currentTimeMillis(), jedis, map);
			for (Index index : indexs) {
				index.queryIndex(query, queryMode);
			}
			contex = queryMode.execute();
		}
		// 查询包含
		BitSet result = contex.toBitSet();

		if (result.isEmpty()) {
			return result;
		}

		// 过滤排除
		BitSet eliminate = contex.eliminate;
		if (!eliminate.isEmpty()) {
			result.andNot(eliminate);
		}
		return result;
	}

	/**
	 * 构建索引
	 * 
	 * @author gengbushuang
	 *
	 */
	private class IndexMode implements IndexBuilder {
		Pipeline pipelined;
		Map<String, Map<String, BitSet>> map;

		public IndexMode(Jedis jedis, Map<String, Map<String, BitSet>> map) {
			this.pipelined = jedis.pipelined();
			this.pipelined.select(1);
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
			for (String s : values) {
				BitSet bitSet = mapTmp.get(s);
				if (bitSet == null) {
					bitSet = new BitSet();
					mapTmp.put(s, bitSet);
				}
				bitSet.set(id);
				pipelined.sadd(ConstantKey.AD_ID + String.valueOf(id), "-" + idenf + ":" + s);
			}

		}

		@Override
		public void set(String key, String... member) {
			setSadd(key, member);
		}

		private void setSadd(String key, String... member) {
			pipelined.sadd(key, member);
		}

		@Override
		public void zset(String key, double score, String member) {
			pipelined.zadd(key, score, member);
		}

		@Override
		public void zset(String key, Map<String, Double> scoreMembers) {
			pipelined.zadd(key, scoreMembers);
		}

		@Override
		public void positiveRow(String key, String... member) {
			pipelined.sadd(ConstantKey.AD_ID + key, member);
		}

	}

	/**
	 * 查询集合操作
	 * 
	 * @author gengbushuang
	 *
	 */
	private class QueryMode implements QueryBuilder {
		Pipeline pipelined;
		Map<String, Map<String, BitSet>> map;
		BitSet bitSet = new BitSet();
		List<String> keyList = new ArrayList<String>();
		final String uid;

		public QueryMode(String uid, Jedis jedis, Map<String, Map<String, BitSet>> map) {
			this.pipelined = jedis.pipelined();
			this.pipelined.select(1);
			this.map = map;
			this.uid = uid;
		}

		@Override
		public void sunion(String dstkey, String... keys) {
			sunionstore(dstkey, keys);
			// queryEliminate(field, keys);
			keyList.add(dstkey);
		}

		@Override
		public void queryEliminate(String field, String... keys) {
			Map<String, BitSet> mb = map.get(field);
			if (mb == null) {
				return;
			}
			for (String key : keys) {
				queryEliminate(mb, key);
			}
		}

		private void queryEliminate(Map<String, BitSet> mb, String key) {
			BitSet b = mb.get(key);
			if (b == null) {
				return;
			}
			bitSet.or(b);
		}

		private void sunionstore(String dstkey, String... keys) {
			pipelined.sunionstore(dstkey, keys);
		}

		public AsyncContex execute() {
			String[] keyArray = keyList.toArray(new String[0]);
			// 无序交集
			pipelined.sinter(keyArray);
			// 有序交集
			// pipelined.zinterstore(uid, keyArray);
			// 获取有序1000的广告
			// pipelined.zrange(uid, 0, 1000);
			// pipelined.del(uid);
			pipelined.del(keyArray);
			return new AsyncContex(pipelined.syncAndReturnAll(), bitSet);
		}

		@Override
		public void zsunion(String field, String dstkey, String... keys) {
			zsunionstore(dstkey + ":" + uid, keys);
			queryEliminate(field, keys);
			keyList.add(dstkey);
		}

		private void zsunionstore(String dstkey, String... keys) {
			pipelined.zunionstore(dstkey + ":" + uid, keys);
		}
	}

	/**
	 * 删除索引
	 * 
	 * @author gengbushuang
	 *
	 */
	//先注释掉,以后删除
//	private class DelMode implements DelBuilder {
//		Pipeline pipelined;
//		Map<String, Map<String, BitSet>> map;
//
//		public DelMode(Jedis jedis, Map<String, Map<String, BitSet>> map) {
//			this.pipelined = jedis.pipelined();
//			this.pipelined.select(1);
//			this.map = map;
//		}
//
//		public void del(String key, String... member) {
//			delSrem(key, member);
//		}
//
//		private void delSrem(String key, String... member) {
//			pipelined.srem(key, member);
//		}
//
//		public void delEliminate(String field, String value, int id) {
//			String[] appArray = StringUtils.splitByWholeSeparator(value, ",");
//			delEliminate(field, appArray, id);
//		}
//
//		private void delEliminate(String idenf, String[] values, int id) {
//			Map<String, BitSet> mapTmp = map.get(idenf);
//			if (mapTmp == null) {
//				return;
//			}
//			for (String s : values) {
//				BitSet bitSet = mapTmp.get(s);
//				if (bitSet == null) {
//					continue;
//				}
//				bitSet.set(id, false);
//			}
//		}
//
//		public void execute() {
//			pipelined.sync();
//		}
//	}

	private static class AsyncContex {
		private List<Object> objects;
		private BitSet eliminate;

		public AsyncContex(List<Object> objects, BitSet bitSet) {
			this.objects = objects;
			this.eliminate = bitSet;
		}

		public BitSet toBitSet() {
			BitSet bitSet = new BitSet();
			if (objects.isEmpty()) {
				return bitSet;
			}

			Object o = objects.get(objects.size() - 2);
			if (o instanceof HashSet) {
				HashSet<String> new_name = (HashSet<String>) o;
				if (!new_name.isEmpty()) {
					for (String s : new_name) {
						bitSet.set(Integer.parseInt(s));
					}
				}
			}
			return bitSet;
		}
	}
}
