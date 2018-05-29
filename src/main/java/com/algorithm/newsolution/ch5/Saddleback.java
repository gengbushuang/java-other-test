package com.algorithm.newsolution.ch5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Saddleback {

	static int[][] createArrays() {
		int[][] w = { 
				{ 1, 2, 3, 4 }, 
				{ 2, 4, 5, 6 }, 
				{ 3, 5, 7, 8 }, 
				{ 4, 6, 8, 9 }
				};
		return w;
	}

	/**
	 * 这个是普通的两次循环寻找
	 * @param w
	 * @param x
	 * @return
	 */
	public String[] solve(int[][] w, int x) {
		int count = 0;
		List<String> tmp = new ArrayList<>();
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				if (w[i][j] == x) {
					tmp.add(i + "x" + j);
				}
				count++;
			}
		}
		System.out.println("solve循环了:"+count);
		return tmp.toArray(new String[0]);
	}

	/**
	 * 是从后往前,从上往下找寻
	 * @param w
	 * @param x
	 * @return
	 */
	public String[] solve2(int[][] w, int x) {
		List<String> tmp = new ArrayList<>();
		int p = 0;
		int z = 0;
		int q = z = w.length-1;

		int z1;
		int count = 0;

		while (p <= z && q >= 0) {
			count++;
			z1 = w[p][q];
			if (z1 < x) {//如果这一行的最后一个小于x,就抛弃这一行
				p = p + 1;
			} else if (z1 > x) {//如果这一行的最后一个大于这个x,就往前找
				q = q - 1;
			} else {//如果这一行找到了,就跳到下一行,前一个继续查找
				tmp.add(p + "x" + q);
				p = p + 1;
				q = q - 1;
			}
		}
		System.out.println("solve2循环了:"+count);
		return tmp.toArray(new String[0]);
	}
	
	

	
	public static void main(String[] args) {
		int[][] w = Saddleback.createArrays();

		Saddleback saddleback = new Saddleback();
		String[] solve = saddleback.solve(w, 2);
		System.out.println(Arrays.toString(solve));
		solve = saddleback.solve2(w, 2);
		System.out.println(Arrays.toString(solve));
	}
}
