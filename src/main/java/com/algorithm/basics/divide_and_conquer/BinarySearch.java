package com.algorithm.basics.divide_and_conquer;

/**
 * @Description:TODO 二分法查找(分治法)
 * @author gbs
 * @Date 2016年10月21日 上午10:59:33
 * @version
 */
public class BinarySearch {

	public static void main(String[] args) {
		int[] a = { 10, 12, 13, 14, 18, 20, 25, 27, 30, 35, 40, 45, 47 };
		int k = 48;
		BinarySearch binarySearch = new BinarySearch();
		int index = binarySearch.search(a, k, 0, a.length - 1);
		System.out.println(index);
		int index2 = binarySearch.iterationSearch(a, k, 0, a.length - 1);
		System.out.println(index2);
	}

	/**
	 * 
	 * @Title: search 
	 * @Description
	 * @param a
	 * @param k
	 * @param left
	 * @param right
	 * @return
	 */
	public int search(int a[], int k, int left, int right) {
		if (a[left] == k) {
			return left;
		}
		if (a[right] == k) {
			return right;
		}
		return recursionSearch(a, k, left, right);
	}

	/**
	 * @Description:TODO 递归版本的查找(二分法查找)
	 * 
	 * @param a
	 * @param left
	 * @param right
	 * @return
	 */
	public int recursionSearch(int a[], int k, int left, int right) {
		if (left > right) {
			return -1;
		}
		int middle = (left + right) / 2;
		if (a[middle] == k) {
			return middle;
		} else if (a[middle] < k) {
			return recursionSearch(a, k, middle + 1, right);
		} else {
			return recursionSearch(a, k, left, middle - 1);
		}
	}

	/**
	 * @Description:TODO 非递归版本查找(二分法查找)
	 * 
	 * @param a
	 * @param k
	 * @param left
	 * @param right
	 * @return
	 */
	public int iterationSearch(int a[], int k, int left, int right) {
		if (a[left] == k) {
			return left;
		}
		if (a[right] == k) {
			return right;
		}
		int middle = 0;
		while (true) {
			if (left > right) {
				middle = -1;
				break;
			}
			//计算中间下标
			middle = (left + right) / 2;
			if (a[middle] == k) {
				break;
			//如果查找的值大于中间数，就重新设置left
			} else if (a[middle] < k) {
				left = middle + 1;
			//如果查找的值小于中间数，就重新设置right
			} else {
				right = middle - 1;
			}
		}
		return middle;
	}
}
