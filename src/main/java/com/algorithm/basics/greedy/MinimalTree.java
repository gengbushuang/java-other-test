package com.algorithm.basics.greedy;

/**
 * 
 * @Description:TODO 最小树的生成(贪婪算法)
 * @author gbs
 * @Date 2016年10月25日 下午5:34:47
 */
public class MinimalTree {

	static int[][] createArrays() {
		int[][] w = { 
				{ 0, 1, 3, Short.MAX_VALUE,Short.MAX_VALUE }, 
				{ 1, 0, 3, 6,Short.MAX_VALUE },
				{ 3, 3, 0, 4,2 }, 
				{ Short.MAX_VALUE,6, 4, 0, 5 },
				{ Short.MAX_VALUE,Short.MAX_VALUE, 2, 5, 0 }
		};
		return w;
	}
	
	
	public void prim(){
		
	}
}
