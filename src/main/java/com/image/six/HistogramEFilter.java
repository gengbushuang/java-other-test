package com.image.six;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.image.four.AbstractBufferedImageOp;

/**
 * 直方图的均衡化
 * @Description:TODO
 * @author gbs
 * @Date 2017年3月24日 上午11:38:05
 */
public class HistogramEFilter extends AbstractBufferedImageOp {

	PixelHSI pixelHSI = new PixelHSI();

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}
		int[] inPixels = new int[width * height];
		double[][] hsiPixels = new double[3][width * height];
		int[] outPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);
		int[] iDataBins = new int[256];
		int[] newiBins = new int[256];
		Arrays.fill(iDataBins, 0);
		Arrays.fill(newiBins, 0);

		int index = 0;
		int totalPixelNumber = height * width;
		for (int row = 0; row < height; row++) {
			int ta = 0, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				ta = (inPixels[index] >> 24) & 0xff;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;

				double[] hsi = pixelHSI.rgbToHSI(new int[] { tr, tg, tb });
				iDataBins[(int) hsi[2]]++;
				hsiPixels[0][index] = hsi[0];
				hsiPixels[1][index] = hsi[1];
				hsiPixels[2][index] = hsi[2];
			}
		}

		generateHEData(newiBins, iDataBins, totalPixelNumber, 256);
		for (int row = 0; row < height; row++) {
			int ta = 255, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				double h = hsiPixels[0][index];
				double s = hsiPixels[1][index];
				double i = newiBins[(int) hsiPixels[2][index]];
				int[] rgb = pixelHSI.hsiToRGB(new double[] { h, s, i });

				tr = clamp(rgb[0]);
				tg = clamp(rgb[1]);
				tb = clamp(rgb[2]);
				outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
			}
		}
		setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}

	private void generateHEData(int[] newiBins, int[] iDataBins, int totalPixelNumber, int count) {
		for(int i=0; i<count; i++) {
			newiBins[i] = getNewintensityRate(iDataBins, totalPixelNumber, i);
		}
	}

	private int getNewintensityRate(int[] iDataBins, int totalPixelNumber, int count) {
		double sum = 0;
		for (int i = 0; i <= count; i++) {
			sum += ((double) iDataBins[i]) / totalPixelNumber;
		}
		return (int) (sum*255.0);
	}

}
