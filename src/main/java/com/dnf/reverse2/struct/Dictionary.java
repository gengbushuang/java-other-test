package com.dnf.reverse2.struct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.dnf.reverse1.ReidsDb;
import com.dnf.reverse2.model.Term;

import redis.clients.jedis.Jedis;

public class Dictionary implements Iterable<Term>{

	Map<String, Integer> indexMap;// 索引信息，键->值
	private List<Term> terms;

	public Dictionary() {
		this(16);
	}

	public Dictionary(int size) {
		terms = new ArrayList<>(size);
		indexMap = new HashMap<>((int) (size / 0.75f) + 1, 0.75f);
	}

	// 获取索引
	public Integer get(String key, String value) {
		String key_value = key + "&" + value;
		return indexMap.get(key_value);
	}

	public void add(Term term) {
		terms.add(term);
		indexMap.put(term.getKey() + "&" + term.getValue(), term.getId());
	}
	
	public void add(Term term,String key,Function<Term, byte[]> function) {
		add(term);
		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);

		jedis.sadd(key.getBytes(), function.apply(term));

		jedis.close();
	}

	public void sort(Comparator<Term> c) {
		Collections.sort(terms, c);
	}
	
	public Term listToMax() {
		int size = terms.size();
		if(size==0) {
			return null;
		}
		return terms.get(size - 1);
	}

	@Override
	public Iterator<Term> iterator() {
		return terms.iterator();
	}

}
