package com.algorithm.basics.backdate;

/**
 * 
 * @Description:TODO n皇后问题(回溯算法)
 * @author gbs
 * @date 2017年1月5日 下午11:24:10
 */
public class NQueen {
	private int size=0;
	int n;
	int [][] a;
	
	public void create(int n){
		this.n=n;
		a = new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				a[i][j]=0;
			}
		}
	}
	
	public void startQuer(){
		for(int i =0;i<n;i++){
			query(0,i);
			System.out.println("==================");
		}
	}
	
	public void query(int i,int j){
		if(i>=n || j>=n){
			return;
		}
		if(i!=0){
			int k = 1;
			for(int tmp = i-1;tmp>=0;tmp--){
				if(a[tmp][j]==1){
					return;
				}
				if(j-k>=0 && a[tmp][j-k]==1){
					return;
				}
				if(j+k<n && a[tmp][j+k]==1){
					return;
				}
				k++;
			}
		}
		a[i][j]=1;
//		for(int b=0;b<i;b++){
//			System.out.print("---");
//		}
		if(i==n-1){
			size++;
		}
//		System.out.println(i+","+j);
		for(int k=0;k<n;k++){
			query(i+1,k);
		}
		a[i][j]=0;
	}
	
	public int getSize() {
		return size;
	}

	public static void  main(String [] args){
		NQueen n = new NQueen();
		n.create(11);
		n.startQuer();
		System.out.println("size="+n.getSize());
	}
}
