package com.image.six.search;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * 16*16*16直方图的特征维度
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年3月27日 下午3:07:49
 */
public class SearchFilter {

	public SearchFilter() {

	}

	public double[] get(BufferedImage src) {
		int width = src.getWidth();
		int height = src.getHeight();

		double[] bins = new double[16 * 16 * 16];
		Arrays.fill(bins, 0);

		int[] inPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);

		int index = 0;
		int level = 16;
		for (int row = 0; row < height; row++) {
			int tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				int ntr = tr / level;
				int ntg = tg / level;
				int ntb = tb / level;
				int nindex = ntr + (ntg + 256 / level) + (ntb + 256 / level * 256 / level);
				bins[nindex]++;
			}
		}
		
		float total = width*height;
		//归一化
		for(int i=0;i<bins.length;i++){
			bins[i] = bins[i]/total;
		}
		return bins;
	}

	private int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		// 颜色类别
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_BGR) {
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		} else {
			return image.getRGB(x, y, width, height, pixels, 0, width);
		}
	}

}
