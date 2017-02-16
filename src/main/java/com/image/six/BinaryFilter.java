package com.image.six;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.image.four.AbstractBufferedImageOp;

public class BinaryFilter extends AbstractBufferedImageOp {

	public final static int MEAN_THRESHOLD = 2;
	public final static int SHIFT_THRESHOLD = 4;

	private int thresholdType;

	public BinaryFilter() {
		this.thresholdType = SHIFT_THRESHOLD;
	}

	public int getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(int thresholdType) {
		this.thresholdType = thresholdType;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}

		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);

		int index = 0;
		int means = (int) getThreshold(inPixels, width, height);
		for (int row = 0; row < height; row++) {
			int ta = 0, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				ta = (inPixels[index] >> 24) & 0xff;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				if (tr > means) {
					tr = tg = tb = 255;
				} else {
					tr = tg = tb = 0;
				}
				outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
			}
		}
		setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}

	private double getThreshold(int[] inPixels, int width, int height) {
		int index = 0;
		double mean = 0.0;
		if (thresholdType == MEAN_THRESHOLD) {
			double sum = 0;
			for (int row = 0; row < height; row++) {
				int tr = 0;
				for (int col = 0; col < width; col++) {
					index = row * width + col;
					tr = (inPixels[index] >> 16) & 0xff;
					sum += tr;
				}
			}
			mean = sum / (width * height);
		} else if (thresholdType == SHIFT_THRESHOLD) {
			mean = getMeanShiftThreshold(inPixels);
		}
		return mean;
	}

	private double getMeanShiftThreshold(int[] inPixels) {
		int itithreshold = 127;
		int finalthreshold = 0;
		int[] tmp = new int[inPixels.length];
		for (int i = 0; i < inPixels.length; i++) {
			tmp[i] = (inPixels[i] >> 16) & 0xff;
		}
		List<Integer> g1 = new ArrayList<Integer>();
		List<Integer> g2 = new ArrayList<Integer>();
		while (finalthreshold != itithreshold) {
			finalthreshold = itithreshold;
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i] <= itithreshold) {
					g1.add(tmp[i]);
				} else {
					g2.add(tmp[i]);
				}
			}
			int m1 = getMeans(g1);
			int m2 = getMeans(g2);
			g1.clear();
			g2.clear();
			itithreshold = (m1 + m2) / 2;
		}
		System.out.println("Final threshold = " + finalthreshold);
		return finalthreshold;
	}

	private static int getMeans(List<Integer> g) {
		int sum = 0;
		int size = g.size();
		for (int i : g) {
			sum += i;
		}
		return sum / size;
	}

}
