package com.algorithm.newsolution.ch1;

import java.util.BitSet;

/**
 * 最小可分配ID
 * 
 * @author gbs
 *
 */
public class MinID {
	private int[] IDS = { 18, 4, 8, 9, 16, 1, 14, 7, 19, 3, 0, 5, 2, 11, 6 };

	/**
	 * 第一种解法,最简单直接 数据量大了后，非常慢，算法性能(n*n,n是集合的长度)
	 * 
	 * @param a
	 * @return
	 */
	public int minId(int[] a) {
		int tmp = 0;
		for (;;) {// n
			if (isContain(a, tmp)) {// n
				return tmp;
			} else {
				tmp += 1;
			}
		}
	}

	/**
	 * 集合里面是否包含这个数值
	 * 
	 * @param a
	 * @param tmp
	 * @return
	 */
	private boolean isContain(int[] a, int tmp) {
		for (int i : a) {
			if (tmp == i) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 改进算法
	 * 用一个长度为n+1的数组，来标记区间[0,n]。
	 * 算法性能时间(n),但是增加了O(n)的空间存储。
	 * @param a
	 * @return
	 */
	public BitSet bitSet = new BitSet();
	
	public int minId2(int[] a) {
		int n = a.length;
		//每次都申请释放空间，变为静态量。
		//改用setbit数据结构更省空间
//		boolean[] b = new boolean[n + 1];
//		Arrays.fill(b, false);
//		for (int i : a) {
//			if (i < n) {
//				b[i] = true;
//			}
//		}
//		for (int i = 0; i <= n; i++) {
//			if (!b[i]) {
//				return i;
//			}
//		}
		///////////////////改进
		for (int i : a) {
			if(i<n){
				bitSet.set(i);
			}
		}
		for (int i = 0; i <= n; i++) {
			if(bitSet.get(i)){
				return i;
			}
		}
		return n + 1;
	}
}
