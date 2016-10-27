package com.algorithm.basics.greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description:TODO 最小树的生成(贪婪算法)
 * @author gbs
 * @Date 2016年10月25日 下午5:34:47
 */
public class MinimalTree {

	static int[][] createArrays() {
		int[][] w = { { 0, 1, 3, Short.MAX_VALUE, Short.MAX_VALUE }, { 1, 0, 3, 6, Short.MAX_VALUE }, { 3, 3, 0, 4, 2 }, { Short.MAX_VALUE, 6, 4, 0, 5 }, { Short.MAX_VALUE, Short.MAX_VALUE, 2, 5, 0 } };
		return w;
	}

	public static void main(String[] args) {
		MinimalTree tree = new MinimalTree();
		tree.prim();
	}

	/**
	 * 
	 * @Description:TODO prim生成最小树
	 * @author gbs
	 */
	public void prim() {
		int[][] createArrays = createArrays();
		int[] nearest = new int[createArrays.length];
		int[] distance = new int[createArrays.length];
		int vnear = 0;
		for (int i = 1; i < createArrays.length; i++) {
			nearest[i] = 1;
			distance[i] = createArrays[0][i];
		}

		List<Integer> list = new ArrayList<Integer>();
		list.add(vnear);
		while (list.size() != createArrays.length) {
			int min = Integer.MAX_VALUE;
			for (int i = 1; i < createArrays.length; i++) {
				if (distance[i]!=-1&&distance[i] < min) {
					min = distance[i];
					vnear = i;
				}
			}
			list.add(vnear);
			//把最小的节点移出,设置-1下次判断就可以过滤掉
			distance[vnear] = -1;
			for (int j = 1; j < createArrays.length; j++) {
				//把跟最新移进节点的周边指更新下
				//就是把列的值跟当前的distance数组值作比较
				//如果distance的值比较大就替换当前列的值
				if (createArrays[j][vnear] < distance[j]) {
					distance[j] = createArrays[j][vnear];
					nearest[j] = vnear;
				}
			}
		}
		System.out.println(list);
	}
}
