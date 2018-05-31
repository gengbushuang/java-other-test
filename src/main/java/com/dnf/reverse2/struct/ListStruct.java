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
import com.dnf.reverse2.model.ID;

import redis.clients.jedis.Jedis;

public class ListStruct<T extends ID> implements Iterable<T> {

	private List<T> lists;

	private Map<T, Integer> maps;

	public ListStruct() {
		this(16);
	}

	public ListStruct(int size) {
		lists = new ArrayList<T>(size);
		maps = new HashMap<>((int) (size / 0.75f) + 1, 0.75f);
	}

	public Integer mapToInteger(T t) {
		return maps.get(t);
	}

	public void sort(Comparator<T> c) {
		Collections.sort(lists, c);
	}

	public T listToMax() {
		int size = lists.size();
		if (size == 0) {
			return null;
		}
		return lists.get(size - 1);
	}

	public void add(T t) {
		lists.add(t);
		maps.put(t, t.getId());
	}

	public void add(T t, String key, Function<T, byte[]> function) {
		add(t);

		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);

		jedis.sadd(key.getBytes(), function.apply(t));

		jedis.close();
	}

	public int search(T t, Comparator<T> c) {
		return Collections.binarySearch(lists, t, c);
	}

	public T indexGet(int index) {
		return lists.get(index);
	}

	@Override
	public Iterator<T> iterator() {
		return lists.iterator();
	}

	public T del(int index) {
		T t = lists.remove(index);
		maps.remove(t, t.getId());
		return t;
	}

	public void del(int index, String key, Function<T, byte[]> function) {
		T t = del(index);

		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);

		jedis.srem(key.getBytes(), function.apply(t));

		jedis.close();
	}

}
