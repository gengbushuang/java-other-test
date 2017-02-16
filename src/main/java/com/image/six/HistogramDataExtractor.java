package com.image.six;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.image.four.AbstractBufferedImageOp;

/**
 * 直方图实现二值化
 * @Description:TODO
 * @author gbs
 * @Date 2017年2月16日 下午5:52:54
 */
public class HistogramDataExtractor extends AbstractBufferedImageOp {

	private int threshold = -1;

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	private int[] histogram;

	public int[] getHistogram() {
		return histogram;
	}

	public void setHistogram(int[] histogram) {
		this.histogram = histogram;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}

		int[] inPixels = new int[width * height];

		getRGB(src, 0, 0, width, height, inPixels);

		histogram = new int[256];
		Arrays.fill(histogram, 0);
		int index = 0;
		for (int row = 0; row < height; row++) {
			int tr = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (inPixels[index] >> 16) & 0xff;
				histogram[tr]++;
			}
		}
		if (threshold > 0) {
			int[] outPixels = new int[width * height];
			for (int row = 0; row < height; row++) {
				int ta = 0, tr = 0, tg = 0, tb = 0;
				for (int col = 0; col < width; col++) {
					index = row * width + col;
					ta = (inPixels[index] >> 24) & 0xff;
					tr = (inPixels[index] >> 16) & 0xff;
					tg = (inPixels[index] >> 8) & 0xff;
					tb = inPixels[index] & 0xff;
					if (tr >= threshold) {
						tr = tg = tb = 255;
					} else {
						tr = tg = tb = 0;
					}
					outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
				}
			}
			setRGB(dest, 0, 0, width, height, outPixels);
		}
		return dest;
	}

}
