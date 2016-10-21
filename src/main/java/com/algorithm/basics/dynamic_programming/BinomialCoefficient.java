package com.algorithm.basics.dynamic_programming;


/**
 * 
 * @Description:TODO 二项式系数(动态规划)
 * @author gbs
 * @Date 2016年10月21日 上午11:17:04
 */
public class BinomialCoefficient {

	public static void main(String[] args) {
		BinomialCoefficient coefficient = new BinomialCoefficient();
		int bin = coefficient.bin(3, 6);
		System.out.println(bin);
		int bin2 = coefficient.bin2(3, 6);
		System.out.println(bin2);
		
	}
	
	/**
	 * 
	 * @Description: TODO 递归实现方式(二项式系数)
	 * @author gbs
	 * @param x
	 * @param y
	 * @return
	 */
	public int bin(int x,int y){
		/*y
		 * 	｜	0	1	2	3	4	x｜
		 * －－－－－－－－－－－－－－－－－
		 *0	｜	1 
		 *1	｜	1	1  
		 *2 ｜	1	2	1
		 *3 ｜	1	3	3	1
		 *4 ｜	1	4	6	4	1
		 *  
		 *  b[1][2]=b[1][2-1]+b[1-1][2-1]=2
		 *  b[2][4]=b[2][4-1]+b[2-1][4-1]=6
		 *  b[x][y]=b[x][y-1]+b[x-1][y-1]
		 */
		if(x==0 || x==y){
			return 1;
		}
		return bin(x,y-1)+bin(x-1,y-1);
	}
	
	/**
	 * 
	 * @Description: TODO 非递归方式(二项式系数)
	 * @author gbs
	 * @param x
	 * @param y
	 * @return
	 */
	public int bin2(int x,int y){
		int [][] a = new int[x+1][y+1];
		for(int i=0;i<=x;i++){
			for(int j=i;j<=y;j++){
				if(i==0||i==j){
					a[i][j]=1;
				}else{
					a[i][j]=a[i][j-1]+a[i-1][j-1];
				}
			}
		}
		return a[x][y];
	}
}
