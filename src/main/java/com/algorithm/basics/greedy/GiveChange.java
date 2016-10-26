package com.algorithm.basics.greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description:TODO 找零钱(贪婪算法)
 * @author gbs
 * @Date 2016年10月25日 下午4:44:23
 */
public class GiveChange {

	List<Integer> list = new ArrayList<Integer>();

	public GiveChange() {
		list.add(50);
		list.add(25);
		list.add(12);
		list.add(10);
		list.add(5);
		list.add(1);
	}

	/**
	 * 
	 * @Description: TODO 找硬币方法
	 * @author gbs
	 * @param n
	 */
	public String find(int n) {
		int s = 0;
		int i = 0;
		StringBuilder sb = new StringBuilder(20);
		//
		while (s != n) {
			// 获取硬币里面面额最大的
			Integer t = list.get(i);
			// 判断加入这个硬币会不会超出找的钱
			if (s + t <= n) {
				s += t;
				// 没有超出加入这个硬币
				sb.append(t).append(",");
			} else {
				// 超出就拒绝这个硬币
				i++;
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		int n = 16;
		new GiveChange().find(n);
	}
}
