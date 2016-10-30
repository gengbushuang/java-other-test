package com.algorithm.basics.greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description:TODO Dijkstra算法(贪婪算法)
 * @author gbs
 * @Date 2016年10月30日 下午1:27:36
 */
public class Dijkstra {

	static int[][] createArrays() {
		int[][] w = { 
				{ 0, 7, 4, 6, 1 }, 
				{ Short.MAX_VALUE,0, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE }, 
				{ Short.MAX_VALUE, 2, 0, 5, Short.MAX_VALUE }, 
				{ Short.MAX_VALUE, 3, Short.MAX_VALUE, 0, Short.MAX_VALUE }, 
				{ Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, 1, 0 } 
				};
//		int[][] w = { 
//				{ 0, 7, 4, 6, 1 }, 
//				{ 7,0, 2, Short.MAX_VALUE, Short.MAX_VALUE }, 
//				{ 4, 2, 0, 5, Short.MAX_VALUE }, 
//				{ 6, 3, 5, 0, 1 }, 
//				{ 1, Short.MAX_VALUE, Short.MAX_VALUE, 1, 0 } 
//				};
		return w;
	}
	
	public void dijkstra(){
		int[][] createArrays = createArrays();
		int[] touch = new int[createArrays.length];
		int[] length = new int[createArrays.length];
		
		int vnear = 0;
		for (int i = 1; i < createArrays.length; i++) {
			touch[i] = 1;
			length[i] = createArrays[0][i];
		}
		List<Integer> list = new ArrayList<Integer>();
		list.add(vnear);
		while (list.size() != createArrays.length) {
			int min = Integer.MAX_VALUE;
			for (int i = 1; i < createArrays.length; i++) {
				if (length[i] != -1 && length[i] < min) {
					min = length[i];
					vnear = i;
				}
			}
			for (int j = 1; j < createArrays.length; j++) {
				if(length[vnear]+createArrays[vnear][j]<length[j]){
					length[j]=length[vnear]+createArrays[vnear][j];
					touch[j] = vnear;
				}
			}
			
			length[vnear] = -1;
			list.add(vnear);
		}
		System.out.println(list);
	}
	
	public static void main(String[] args) {
		Dijkstra dijkstra=new Dijkstra();
		dijkstra.dijkstra();
	}
}
