package com.algorithm.basics.dynamic_programming;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import sun.net.www.content.audio.wav;

/**
 * 
 * @Description:TODO 旅行推销员问题(动态规划)
 * @author gbs
 * @Date 2016年10月21日 上午11:21:47
 */
public class TravelingSalesman {
	static int[][] t = createArrays();
	static int[][] createArrays() {
		int[][] w = { { 0, 2, 9, Short.MAX_VALUE }, { 1, 0, 6, 4 }, { Short.MAX_VALUE, 7, 0, 8 }, { 6, 3, Short.MAX_VALUE, 0 } };
		return w;
	}

	/**
	 * sub代表下标 V代表顶点的集合 A代表经过的顶点集合
	 * 
	 * 最优旅程的长度=minimum(w[1][j]+D[v<sub>j</sub>)][V-{v<sub>1</sub>,v<sub>j</sub>}
	 * ] D[v<sub>i</sub>][A]=minimum(w[i][j]+D[v<sub>j</sub>][A-{v<sub>j</sub>}]
	 * )
	 * 
	 * 
	 * @param w
	 * @param p
	 */
	public void travel(int[][] w, int[][] p) {
		int n = w.length;
		System.out.println("顶点有：" + n + "个");
		for (int k = 1; k <= n-1; k++) {
			System.out.println(k);
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < n; j++) {
					if(i!=0){
						System.out.println("w[" + i + "][" + j + "]D["+j+"]");
					}
//						if (k == 0) {
//							System.out.println("w[" + i + "][" + j + "]+D[" + j + "][" + 1 + "]");
//						} else {
//							System.out.println("w[" + i + "][" + j + "]+D[" + j + "]");
//						}
//					}
				}
			}
			break;
		}
	}
	
	public void test(){
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		int test1 = test1(list.get(0), list,list.size()-1);
		System.out.println(test1);
		
	}
	
	/**
	 * 
	 * @Description: TODO 递归方式的实现
	 * @author gbs
	 * @param x
	 * @param list
	 * @param count
	 * @return
	 */
	public int test1(int x, List<Integer> list,int count){
		if(list.size()==1){
			return t[list.get(0).intValue()][0];
		}
		
		List<Integer> listTmp = new ArrayList<Integer>(count);
		for(int i=0;i<list.size();i++){
			if(x==list.get(i).intValue()){
				continue;
			}
			listTmp.add(list.get(i));
		}
		int minlenth = Integer.MAX_VALUE;
		for(int i=0;i<listTmp.size();i++){
			int t1 = test1(listTmp.get(i), listTmp,listTmp.size());
			int t2 = t[x][listTmp.get(i).intValue()]+t1;
			System.out.println("W["+x+","+listTmp.get(i)+"],result="+t2);
			minlenth = Integer.min(minlenth, t2);
		}
		return minlenth;
	}
	
	

	public static void main(String[] args) {
		int[][] w = createArrays();
		int[][] p = new int[w.length][w.length];
		TravelingSalesman salesman = new TravelingSalesman();
//		salesman.travel(w, p);
		salesman.test();
	}

}
