package com.algorithm.basics.dynamic_programming;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description:TODO 旅行推销员问题(动态规划)
 * @author gbs
 * @Date 2016年10月21日 上午11:21:47
 */
public class TravelingSalesman {
	static int[][] createArrays(){
		int [][] w = {
				{0,2,9,Short.MAX_VALUE},
				{1,0,6,4},
				{Short.MAX_VALUE,7,0,8},
				{6,3,Short.MAX_VALUE,0}
		};
		return w;
	}
	
	/**
	 * sub代表下标
	 * V代表顶点的集合
	 * A代表经过的顶点集合
	 * 
	 * 最优旅程的长度=minimum(w[1][j]+D[v<sub>j</sub>)][V-{v<sub>1</sub>,v<sub>j</sub>}]
	 * D[v<sub>i</sub>][A]=minimum(w[i][j]+D[v<sub>j</sub>][A-{v<sub>j</sub>}])
	 * 
	 * 
	 * @param w
	 * @param p
	 */
	public void travel(int [][] w,int [][] p){
		int n = w.length;
		System.out.println("顶点有："+n+"个");
		for(int k=0;k<=n-2;k++){
			System.out.println(0);
			for(int i = 0;i<w.length;i++){
				if(i==k){
					continue;
				}
				System.out.println("--"+i);
			}
//			for(int i =0;i<n;i++){//要经过的顶级集合A
//				System.out.println("["+i+"]["+j+"]");
//			}
//			for(int j = i+1;j<w.length;j++){
//				System.out.println("["+i+"]["+j+"]");
//				for(int k=1;k<w.length;k++){
//					if(j==k){
//						continue;
//					}
//					System.out.println("  ["+j+"]["+k+"]");
//				}
//			}
		}
	}
	
	public static void main(String[] args) {
		int[][] w = createArrays();
		int[][] p = new int [w.length][w.length];
		TravelingSalesman salesman = new TravelingSalesman();
		salesman.travel(w,p);
	}
	
}
