package com.image.six;

public class PixelMean {

	/**
	 * 计算图像像素的均值 mean=Sum(P[x,y])/(x*y)
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param width
	 * @param height
	 * @param pixels
	 */
	public void mean(int width, int height, int[] pixels) {
		int index = 0;
		double sum = 0;
		for (int row = 0; row < height; row++) {
			int tr = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (pixels[index] >> 16) & 0xff;
				sum += tr;
			}
		}
		double mean = sum / (width * height);
	}
}
