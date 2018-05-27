package com.dnf.reverse1;

public interface QueryBuilder {
	/**
	 * set交集
	 * 
	 * @param field
	 * @param dstkey
	 * @param keys
	 */
	public void sunion(String field, String dstkey, String... keys);

	/**
	 * set交集(有序集合)
	 * 
	 * @param field
	 * @param dstkey
	 * @param keys
	 */
	public void zsunion(String field, String dstkey, String... keys);

}
