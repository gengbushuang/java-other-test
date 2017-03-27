package com.image.six;

import java.util.Arrays;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

/**
 * 统计像素直方图
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年2月16日 上午10:52:03
 */
public class PixelHistogram {

	/**
	 * ∑(g~255)P(g)=h(g)/M 
	 * m=width*height,h(g)表示灰度为g的像素出现频率，g的取值范围为0～255之间。
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param width
	 * @param height
	 * @param pixels
	 */
	public void histogram(int width, int height, int[] pixels) {
		int[] histogram = new int[256];
		Arrays.fill(histogram, 0);
		int index = 0;
		for (int row = 0; row < height; row++) {
			int tr = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (pixels[index] >> 16) & 0xff;
				histogram[tr]++;
			}
		}
		int [] newHistogram = new int[256];
		//
		for (int i = 0; i < 256; i++) {
			int sum = 0;
			for (int j = 0; j <= i; j++) {
				sum += histogram[j] / width * height;
			}
			newHistogram[i] = sum;
		}
		
		double maxFrequency = 0;
		for (int i = 0; i < histogram.length; i++) {
			maxFrequency = Math.max(maxFrequency, histogram[i]);
		}
	}
}
