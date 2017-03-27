package com.image.six.search;

public class SearchSimilar {

	/**
	 * 欧几里得距离
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param src
	 * @param desc
	 * @return 如果一样返回0，越大越不一样
	 */
	public double euclid(double[] src, double[] desc) {
		if (src.length != desc.length) {
			return -1;
		}
		double sum = 0;
		for (int i = 0; i < src.length; i++) {
			sum += Math.pow((src[i] - desc[i]), 2);
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * 巴氏系数
	 * @Description: TODO
	 * @author gbs
	 * @param src
	 * @param desc
	 * @return 值在[0-1]之间，1为完全相同，0为完全不同
	 */
	public double bhattacharyyaCoefficient(double[] src, double[] desc){
		if (src.length != desc.length) {
			return -1;
		}
		double [] mixeData = new double[src.length];
		for (int i = 0; i < src.length; i++) {
			mixeData[i] = Math.sqrt(src[i]*desc[i]);
		}
		
		double sum = 0;
		for(int i = 0;i<mixeData.length;i++){
			sum+=mixeData[i];
		}
		return sum;
	}
}
