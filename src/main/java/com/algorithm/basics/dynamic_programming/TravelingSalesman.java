package com.algorithm.basics.dynamic_programming;

/**
 * 旅行推销员问题
 * @author gbs
 *
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
		
	}
	
	public static void main(String[] args) {
		int[][] w = createArrays();
		int[][] p = new int [w.length][w.length];
		TravelingSalesman salesman = new TravelingSalesman();
		salesman.travel(w,p);
	}
	
}
