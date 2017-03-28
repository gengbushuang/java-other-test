package com.image.seven;

import java.awt.image.BufferedImage;

import com.image.four.AbstractBufferedImageOp;

public class ZoomFilter extends AbstractBufferedImageOp {

	// 像素替换放大
	public final static int PIXEL_TIMES_ZOOM = 1;
	// 零阶保持放大
	public final static int ZERO_TIMES_ZOOM = 2;
	// k次放大
	public final static int K_TIMES_ZOOM = 3;

	public final int zoomType;

	public int times;

	public ZoomFilter(int zoom_type) {
		this.zoomType = zoom_type;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();

		int[] inPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);

		int nwidth = width * times;
		int nheight = height * times;
		
		if (dest == null) {
			dest = createCompatibleDestImage(src, nwidth,nheight,null);
		}
		
		int[] outPixels = new int[nwidth * nheight];
		int index = 0;
		for (int row = 0; row < nheight; row++) {
			for (int col = 0; col < nwidth; col++) {
				index = row * nwidth + col;
//				System.out.print(row + "x" + col + " ");
				int nrow = (int) (row / times);
				int ncol = (int) (col / times);
				int index2 = nrow * width + ncol;
				outPixels[index] = inPixels[index2];
//				System.out.print(nrow + "nx" + ncol + " ");
			}
//			 System.out.println();
		}
		setRGB(dest, 0, 0, nwidth, nheight, outPixels);
		return dest;
	}

}
