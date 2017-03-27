package com.image.six;

public class PixelVariance {

	/**
	 * 计算像素方差 stdev = (Sum(P[x,y]*P[x,y])/(X*Y))-(mean*mean)
	 * 根方 var = stdev*stdev
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param width
	 * @param height
	 * @param pixels
	 * @param mean
	 */
	public void variance(int width, int height, int[] pixels, int mean) {
		int index = 0;
		double sum = 0;
		for (int row = 0; row < height; row++) {
			int tr = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (pixels[index] >> 16) & 0xff;
				sum += (tr * tr);
			}
		}
		double stdev = sum / (width * height) - Math.pow(mean, 2);
	}
}
