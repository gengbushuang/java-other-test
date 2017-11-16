package com.thinkstats.one;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.Lists;

public class Thinkstats {

	/**
	 * 中位数
	 * @Description: TODO
	 * @author gbs
	 * @param fs
	 * @return
	 */
	public float median(float[] fs) {
		int middle = fs.length >> 1;
		float tmp;
		if ((fs.length & 0x1) == 1) {
			tmp = fs[middle - 1];
		} else {
			tmp = (fs[middle - 1] + fs[middle]) / 2;
		}
		return tmp;
	}
	
	public void mode(float[] fs) {
		TreeMap<String, Integer> m = new TreeMap<String, Integer>();
		for (float f : fs) {
			if (m.containsKey(String.valueOf(f))) {
				Integer integer = m.get(String.valueOf(f));
				m.put(String.valueOf(f), integer.intValue() + 1);
			} else {
				m.put(String.valueOf(f), 1);
			}
		}
		System.out.println(m);
	}

	/**
	 * 方差
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param fs
	 * @return
	 */
	public float variance(float[] fs) {
		float mu = mean(fs);
		return variance(fs, mu);
	}

	/**
	 * 方差
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param fs
	 * @param mu
	 * @return
	 */
	public float variance(float[] fs, float mu) {
		float[] tmp = new float[fs.length];
		for (int i = 0; i < fs.length; i++) {
			tmp[i] = (fs[i] - mu) * (fs[i] - mu);
		}
		return mean(tmp);
	}

	/**
	 * 均值
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param fs
	 * @return
	 */
	public float mean(float[] fs) {
		return sum(fs) / fs.length;
	}

	/**
	 * 求和
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param fs
	 * @return
	 */
	public float sum(float[] fs) {
		float count = 0.0f;
		for (float f : fs) {
			count += f;
		}
		return count;
	}
	
	/**
	 * 排列
	 * 
	 * 从n个不同元素中取出m个元素的所有排列的个数，叫做从n个不同元素中取出m个元素的排列数，记为Amn.
	 * 排列数的公式：Amn=n（n-1）（n-2）…（n-m+1）
	 * @Description: TODO
	 * @author gbs
	 * @param n 总共多少个
	 * @param m 取出多少个排列
	 * @return
	 */
	public float permutation(int n, int m) {
		if (n < m) {
			return 0;
		}
		float sum = n - m + 1;
		for (int i = 0, t = m - 1; i < t; i++) {
			sum *= (n - i);
		}
		return sum;
	}
	
	/**
	 * 组合
	 * 
	 * 从n个不同元素中取出m个元素的所有组合的个数，叫做从n个不同元素中取出m个元素的组合数，用符号Cmn表示。
	 * 
	 * Cmn=Amn/m!
	 * @Description: TODO
	 * @author gbs
	 * @param n
	 * @param m
	 * @return
	 */
	public float combination(int n, int m) {
		if (n < m) {
			return 0;
		}
		float numerator = permutation(n, m);
		float denominator = permutation(m, m);
		return (numerator / denominator);
	}

	public static void main(String[] args) {
		//3.95424264e21
		Thinkstats thinkstats = new Thinkstats();
		float c1 = thinkstats.combination(13, 9);
		System.out.println(c1);
		float c2 = thinkstats.combination(39, 4);
		System.out.println(c2);
		float c3 = thinkstats.combination(52, 13);
		System.out.println(c3);
		float a = (c1*c2)/c3;
		System.out.println(a);
	}
}
