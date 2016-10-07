package com.algorithm.basics.divide_and_conquer;

import java.util.Arrays;
import java.util.Random;

/**
 * 分治法 合并排序
 * 
 * @author gbs
 *
 */
public class MergeSort {

	public static void main(String[] args) {
		// System.out.println(s.length);
		int[] a = { 27, 10, 12, 20, 25, 13, 15, 22 };
		int n = 15;
		a = new int[n];
		Random r = new Random();
		for (int i = 0; i < a.length; i++) {
			a[i] = r.nextInt(10*10);
		}
		System.out.println(Arrays.toString(a));
		MergeSort mergeSort = new MergeSort();
		mergeSort.mergesSort(a, 0, a.length - 1);
		System.out.println(Arrays.toString(a));
	}

	public void mergesSort(int a[], int left, int right) {
		// if (left + 1 > right) {
		// return;
		// }
		if (right > left) {
			int middle = (left + right) / 2;
			mergesSort(a, left, middle);
			mergesSort(a, middle + 1, right);
			merges(a, left, middle, right);
		}
	}

	private void merges(int[] a, int left, int middle, int right) {
//		System.out.println(left + "," + middle + "," + right);
		int l = left, m = middle, r = right;
		if (l == m) {
			if (a[m] > a[r]) {
				swap(a, m, r);
			}
		} else {
			int size = (right - left) + 1;
			int[] tmp = new int[size];
			int i = 0;
			 m++;
			for (; l <= middle && m <= right;) {
				if (a[l] > a[m]) {
					tmp[i++] = a[m];
					m++;
				} else {
					tmp[i++] = a[l];
					l++;
				}
			}
			//改进后的合并排序
			if(l<=middle){
				System.arraycopy(a, l, tmp, i, size-i);
			}else{
				System.arraycopy(a, m, tmp, i, size-i);
			}
//			System.out.println("-->"+Arrays.toString(tmp));
			//下面是以前的合并排序
//			while (i < size && l <= middle) {
//				tmp[i++] = a[l];
//				l++;
//			}
//			while (i < size && m <= right) {
//				tmp[i++] = a[m];
//				m++;
//			}
//			System.out.println(Arrays.toString(tmp));
			System.arraycopy(tmp, 0, a, left, tmp.length);
		}
//		System.out.println(Arrays.toString(a));
	}
	
	private void swap(int[] a, int index1, int index2) {
		int tmp = a[index1];
		a[index1] = a[index2];
		a[index2] = tmp;
	}
}
