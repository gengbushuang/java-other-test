package com.data_structure.tree;

import java.util.Arrays;

/**
 * 
 * @Description:TODO 不相交的数据结构
 * @author gbs
 * @Date 2016年10月30日 上午5:54:03
 */
public class DisjSets {

	private int [] s;
	
	public DisjSets(int count){
		s = new int [count];
		Arrays.fill(s, -1);
	}
	
	/**
	 * 
	 * @Description: TODO 查找子集合的父节点
	 * @author gbs
	 * @param x
	 * @return
	 */
	public int find(int x){
		int index = x;
		while(s[index]>-1){
			index = s[index];
		}
		
		return index;
	}
	
	/**
	 * 
	 * @Description: TODO 合并集合
	 * @author gbs
	 * @param x1
	 * @param x2
	 */
	public void union(int x1,int x2){
		//如果一个集合x1高度大于另一个集合x2高度
		//让x2的集合根节点变为x1的下标，x2变为子集合
		if(s[x1]<s[x2]){
			s[x2]=x1;
		}else{
			//如果两个集合高度相等，把变为根节点的那个高度加1
			if(s[x1]==s[x2]){
				s[x2]--;
			}
			s[x1]=x2;
		}
	}
	
	/**
	 * 
	 * @Description: TODO 判断是否在一个集合里面
	 * @author gbs
	 * @param x1
	 * @param x2
	 * @return true为是false不是
	 */
	public boolean equal(int x1,int x2){
		if(x1==x2){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "DisjSets [s=" + Arrays.toString(s) + "]";
	}

	public static void main(String[] args) {
		DisjSets disjSets = new DisjSets(6);
		int find1 = disjSets.find(1);
		int find2 = disjSets.find(2);
		
		disjSets.union(find1, find2);
		
		int find = disjSets.find(1);
		System.out.println(find);
		
		System.out.println(disjSets);
	}
}
