package com.algorithm.newsolution.ch2;

import java.util.Random;

public class InsertSort {

	/**
	 * 插入排序
	 * 从右边向左边扫描
	 * @Description: TODO
	 * @author gbs
	 */
	public void sort1(int[] a) {
		int n = a.length;
		int tmp;
		for (int i = 1; i < n; i++) {
			tmp = a[i];
			int j = i - 1;
			//判断当前的下标是否小于前一个下标
			//是的话就进行位置调换把前一个下标的值赋值给当前下标，在移动位置把前一个下标变成当前下标重复比对。
			while (j >= 0 && tmp < a[j]) {
				a[j + 1] = a[j];
				j -= 1;
			}
			a[j + 1] = tmp;
		}
	}
	
	/**
	 * 改进插入排序，利用二分查找，比较次数变成了o(logn)，插入还是O(n*n)
	 * @Description: TODO
	 * @author gbs
	 * @param a
	 */
	public void sort2(int[] a) {
		int n = a.length;
		int tmp;
		for (int i = 1; i < n; i++) {
			tmp = a[i];
			int p = binary_search(a, i, tmp);
			for (int j = i; j > p; j--) {
				a[j] = a[j-1];
			}
			a[p] = tmp;
			show(a);
		}
	}
	
	private int binary_search(int a[],int len,int tmp){
		int l = 0;
		int u = len;
		while (l < u) {
			int m = (l + u) / 2;
			if (a[m] == tmp) {
				return m;
			} else if (a[m] < tmp) {
				l = m + 1;
			} else {
				u = m;
			}
		}
		return l;
	}
	
	public void show(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	

	public static void main(String[] args) {
		InsertSort sort = new InsertSort();
		int n = 10;
		int[] a = new int[n];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			a[i] = r.nextInt(100);
		}
		sort.show(a);
		System.out.println("------------------start------------------");
		sort.sort2(a);
		System.out.println("------------------end------------------");
		sort.show(a);
	}
}
