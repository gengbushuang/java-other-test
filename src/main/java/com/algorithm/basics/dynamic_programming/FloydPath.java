package com.algorithm.basics.dynamic_programming;

/**
 * 
 * @Description:TODO Floyd最短路径(动态规划)
 * @author gbs
 * @Date 2016年10月21日 上午11:18:31
 */
public class FloydPath {

	static int[][] createArrays(){
		int [][] w = {
				{0,1,Short.MAX_VALUE,1,5},
				{9,0,3,2,Short.MAX_VALUE},
				{Short.MAX_VALUE,Short.MAX_VALUE,0,4,Short.MAX_VALUE},
				{Short.MAX_VALUE,Short.MAX_VALUE,2,0,3},
				{3,Short.MAX_VALUE,Short.MAX_VALUE,Short.MAX_VALUE,0}
		};
		return w;
	}
	
	/**
	 * 输入一个加权图
	 * D(k)[i][j]=minimum(D(k-1)[i][j],D(k-1)[i][k]+D(k-1)[k][j])
	 * d<sup>(k)</sup>[i][j]=minimum(d<sup>(k-1)</sup>[i][j],d<sup>(k-1)</sup>[i][k]+d<sup>(k-1)</sup>[k][j])
	 * @param n
	 * @param w
	 * @return
	 */
	public int[][] floyd(int n,int [][] w){
		int [][] d = w.clone();
		for(int i=0;i<d.length;i++){
			d[i] = w[i].clone();
		}
		
		
		for(int k = 0;k<n;k++){
			for(int i = 0;i<n;i++){
				for(int j =0;j<n;j++){
					d[i][j]=Integer.min(d[i][j], d[i][k]+d[k][j]);
				}
			}
		}
		return d;
	}
	
	/**
	 * 建立最短路径的索引p数组
	 * @param n
	 * @param w
	 * @return
	 */
	public int[][] floydToIndex(int n,int [][] w){
		int [][]p = new int[n][];
		for(int i=0;i<n;i++){
			p[i] = new int[n];
			for(int j=0;j<n;j++){
				p[i][j]=0;
			}
		}
		
		int [][] d = w.clone();
		for(int i=0;i<d.length;i++){
			d[i] = w[i].clone();
		}
		
		for(int k = 0;k<n;k++){
			for(int i = 0;i<n;i++){
				for(int j =0;j<n;j++){
					if(d[i][j]>d[i][k]+d[k][j]){
						p[i][j] = k;
						d[i][j] = d[i][k]+d[k][j];
					}
				}
			}
		}
		//利用索引输出最短路径
		indexByPath(p, d);
		return p;
	}
	
	public void show(int [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[i].length;j++){
				System.out.print(a[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("-------------------------");
	}
	
	public void indexByPath(int[][]index,int[][]w){
		for(int i = 0;i<index.length;i++){
			for(int j=0;j<index[i].length;j++){
				if(index[i][j]!=0){
					System.out.println((i+1)+","+(index[i][j]+1)+","+(j+1)+"="+(w[i][index[i][j]]+w[index[i][j]][j]));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		FloydPath floydPath = new FloydPath();
		int[][] w = FloydPath.createArrays();
		floydPath.show(w);
		int[][] d = floydPath.floyd(5, w);
		floydPath.show(d);
		System.out.println("============================");
		floydPath.floydToIndex(5, w);
//		floydPath.show(index);
//		floydPath.indexByPath(index, w);
	}
}
