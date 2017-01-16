package com.utils;

public class HashUtils {

	public static int HashFNVByString(String str) {
		byte[] bytes = str.getBytes();
		return HashFNVByByte(bytes);
	}
	
	/**
	 * 在阿里云上看到的
	 * 改进的32位FNV算法，高离散
	 * @Description: TODO
	 * @author gbs
	 * @param bytes
	 * @return
	 */
	public static int HashFNVByByte(byte[] bytes) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (byte b : bytes) {
			hash = (hash ^ b) * p;
		}
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}
}
