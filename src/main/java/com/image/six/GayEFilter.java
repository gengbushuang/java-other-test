package com.image.six;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.image.four.AbstractBufferedImageOp;

/**
 * 灰度直方图的均衡化
 * @Description:TODO
 * @author gbs
 * @Date 2017年3月27日 上午11:25:52
 */
public class GayEFilter extends AbstractBufferedImageOp{

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();
		
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}
		
		int[] histogram = new int[256];
		int [] newHistogram = new int[256];
		Arrays.fill(histogram, 0);
		Arrays.fill(newHistogram, 0);
		
		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);
		int index = 0;
		int totalPixelNumber = height * width;
		for (int row = 0; row < height; row++) {
			int tg = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tg = (inPixels[index] >> 8) & 0xff;
				histogram[tg]++;
				// outPixels[index] = (255 << 24) | (tg << 16) | (tg << 8) | tg;
			}
		}
		
		gayHEData(newHistogram, histogram, totalPixelNumber, 256);
		
		for (int row = 0; row < height; row++) {
			int tg = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tg = (inPixels[index] >> 8) & 0xff;
				int g = newHistogram[tg];
				outPixels[index] = (255 << 24) | (g << 16) | (g << 8) | g;
			}
		}
		
		setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}

	private void gayHEData(int[] newHistogram, int[] histogram, int totalPixelNumber, int count) {
		for(int i=0; i<count; i++) {
			newHistogram[i] = getNewintensityRate(histogram, totalPixelNumber, i);
		}
	}

	private int getNewintensityRate(int[] histogram, int totalPixelNumber, int count) {
		double sum = 0;
		for (int i = 0; i <= count; i++) {
			sum += ((double) histogram[i]) / totalPixelNumber;
		}
		return (int) (sum*255.0);
	}

}
