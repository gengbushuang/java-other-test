package com.dnf.reverse1;

import java.util.Map;

public interface IndexBuilder {

	/**
	 * 正排关联
	 * 
	 * @param key
	 * @param member
	 */
	public void positiveRow(String key, String... member);

	/**
	 * 无序队列
	 * 
	 * @param key
	 * @param member
	 */
	public void set(String key, String... member);

	/**
	 * 有序队列
	 * 
	 * @param key
	 * @param score
	 * @param member
	 */
	public void zset(String key, double score, String member);

	/**
	 * 有序队列
	 * 
	 * @param key
	 * @param scoreMembers
	 */
	public void zset(String key, Map<String, Double> scoreMembers);

	/**
	 * 排除构建，map->map->id
	 * 
	 * @param idenf 第一层key
	 * @param apps 第二层的key
	 * @param id 第三层的数据
	 */
	public void eliminate(String idenf, String apps, int id);
}
