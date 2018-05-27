package com.dnf.reverse1;

import java.util.Map;

public interface IndexBuilder {
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
	 * 排除
	 * 
	 * @param idenf
	 * @param apps
	 * @param id
	 */
	public void eliminate(String idenf, String apps, int id);
}
