package com.loadclass;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.utils.HashUtils;

/**
 * 阿里云上的
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2016年12月21日 下午12:19:31
 * @param <T>
 */
public class Shard<T> {

	private TreeMap<Long, T> treeNode;
	private List<T> shardInfos;
	private final int NODE_NUM = 10;

	public Shard(List<T> shardInfos) {
		this.shardInfos = shardInfos;
		init();
	}

	private void init() {
		treeNode = new TreeMap<Long, T>();  
		for (int i = 0; i < shardInfos.size(); i++) {
			T t = shardInfos.get(i);
			for (int n = 0; n < NODE_NUM; n++) {
				int hash = HashUtils.HashFNVByString("SHARD-" + i + "-NODE-" + n);
				System.out.println(hash+"==="+t);
				treeNode.put((long) hash, t);
			}
		}
	}
	
	public T getShardInfo(String key) {
		int hash = HashUtils.HashFNVByString(key);
		SortedMap<Long, T> tailMap = treeNode.tailMap((long)hash);
		if(tailMap.size()==0){
			return treeNode.get(treeNode.firstKey());
		}
		return tailMap.get(tailMap.firstKey());
	}
}
