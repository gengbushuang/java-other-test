package com.dnf.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

public class IndexMode {

	Map<String, Map<String, BitSet>> map = null;

	public IndexMode() {
		readEliminateIndex("D:/index.tl");
		if (map == null) {
			map = new HashMap<String, Map<String, BitSet>>();
		}

	}

	public void wirteEliminateIndex(String path) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(path)))) {
			outputStream.writeObject(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public void setSadd(String key, String id) {
		try (Jedis jedis = ReidsDb.DB().getJedis();) {
			jedis.select(1);
			jedis.sadd(key, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Object> query(Query query, QueryCall queryCall) {
		try (Jedis jedis = ReidsDb.DB().getJedis();) {
			return queryCall.query(query, jedis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public void queryEliminate(String field, String[] keys, BitSet bitSet) {
		Map<String, BitSet> mb = map.get(field);
		if (mb == null) {
			return;
		}
		for (String key : keys) {
			queryEliminate(mb, key, bitSet);
		}
	}

	public void queryEliminate(String field, String key, BitSet bitSet) {
		Map<String, BitSet> mb = map.get(field);
		if (mb == null) {
			return;
		}
		queryEliminate(mb, key, bitSet);

	}

	private void queryEliminate(Map<String, BitSet> mb, String key, BitSet bitSet) {
		BitSet b = mb.get(key);
		if (b == null) {
			return;
		}
		bitSet.or(b);
	}

	public void eliminate(String idenf, String apps, int id) {
		String[] appArray = StringUtils.splitByWholeSeparator(apps, ",");
		Map<String, BitSet> mapTmp = map.get(idenf);
		if (mapTmp == null) {
			mapTmp = new TreeMap<>();
			map.put(idenf, mapTmp);
		}
		for (String app : appArray) {
			BitSet bitSet = mapTmp.get(app);
			if (bitSet == null) {
				bitSet = new BitSet();
				mapTmp.put(app, bitSet);
			}
			bitSet.set(id);
		}
	}
}
