package com.algorithm.basics.divide_and_conquer;

import java.util.Arrays;
import java.util.Random;

/**
 * 分治法 快速排序
 * 
 * @author gbs
 *
 */
public class QuickSort {

	public static void main(String[] args) {
		QuickSort sort = new QuickSort();
		int n = 15;
		int[] a = new int[n];
		Random r = new Random();
		for (int i = 0; i < a.length; i++) {
			a[i] = r.nextInt(10 * 10);
		}
		System.out.println(Arrays.toString(a));
		sort.sort(a, 0, a.length-1);
		System.out.println(Arrays.toString(a));
	}

	public void sort(int[] a, int left, int right) {

		if (right > left) {
			int middle = partition(a, left, right);
//			System.out.println("middle-->"+middle);
			sort(a, left, middle-1);
			sort(a, middle+1, right);
		}
	}

	private int partition(int[] a, int left, int right) {
		int tmp = a[left];
		int l = left, r = right;
		//这是改进后的
		for(int i=l+1;i<=r;i++){
			if(tmp>a[i]){
				l++;
				swap(a, l, i);
			}
			
		}
		//这是修改前的
//		for (;;) {
//			while (a[r] > tmp) {
//				r--;
//			}
//			while (a[l] < tmp) {
//				l++;
//			}
//			if (l < r) {
//				int t = a[l];
//				a[l] = a[r];
//				a[r] = t;
//			} else {
//				break;
//			}
//		}
		swap(a, left, l);
		return l;
	}
	
	private void swap(int[] a, int index1, int index2) {
		int tmp = a[index1];
		a[index1] = a[index2];
		a[index2] = tmp;
	}
}
