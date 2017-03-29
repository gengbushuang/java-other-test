package com.image.seven;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.image.four.AbstractBufferedImageOp;

/**
 * 图像放大
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年3月29日 上午10:04:45
 */
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
		if (zoomType == ZERO_TIMES_ZOOM) {
			dest = zeroZoom(inPixels, width, height,src,dest);
		} else if (zoomType == K_TIMES_ZOOM) {
			kZoom(inPixels, width, height,dest);
		} else {
			dest = pixelZoom(inPixels, width, height,src,dest);
		}
		// if (dest == null) {
		// dest = createCompatibleDestImage(src, nwidth,nheight,null);
		// }
		//
		// setRGB(dest, 0, 0, nwidth, nheight, outPixels);
		return dest;
	}

	private BufferedImage zeroZoom(int[] inPixels, int width, int height,BufferedImage src,BufferedImage dest) {
		int nwidth = width + width - 1;
		int nheight = height + height - 1;
		int[] outPixels = new int[nwidth * nheight];
		Arrays.fill(outPixels, 0);
		int nindex = 0;
		for (int row = 0; row < nheight; row++) {
			if (row % 2 != 0) {
				continue;
			}
			for (int col = 0; col < nwidth; col++) {
				nindex = row * nwidth + col;
				int tmpcol = col / 2;
				int tmprow = row / 2;
				int oindex1 = tmprow * width + tmpcol;
				if (col % 2 == 0) {
					outPixels[nindex] = inPixels[oindex1];
				} else {
					int oindex2 = tmprow * width + (tmpcol + 1);
					sawpPixels(outPixels,nindex,inPixels,oindex1,oindex2);
				}
			}
		}
		
		
		for (int row = 0; row < nheight; row++) {
			if (row % 2 == 0) {
				continue;
			}
			for (int col = 0; col < nwidth; col++) {
				nindex = row * nwidth + col;
				int oindex1 = (row-1) * nwidth + col;
				int oindex2 = (row + 1) * nwidth + col;
				sawpPixels(outPixels, nindex, outPixels, oindex1, oindex2);
			}
		}
		
		if(dest==null){
			dest = createCompatibleDestImage(src,nwidth,nheight,null);
		}
		setRGB(dest, 0, 0, nwidth, nheight, outPixels);
		return dest;
	}
	
	private void sawpPixels(int[] outPixels, int nindex, int[] inPixels, int oindex1, int oindex2) {
		int ta1 = (inPixels[oindex1] >> 24) & 0xff;
		int tr1 = (inPixels[oindex1] >> 16) & 0xff;
		int tg1 = (inPixels[oindex1] >> 8) & 0xff;
		int tb1 = inPixels[oindex1] & 0xff;
		
//		int ta2 = (inPixels[oindex2] >> 24) & 0xff;
		int tr2 = (inPixels[oindex2] >> 16) & 0xff;
		int tg2 = (inPixels[oindex2] >> 8) & 0xff;
		int tb2 = inPixels[oindex2] & 0xff;
		
		int tr3 = (tr1+tr2)/2;
		int tg3 = (tg1+tg2)/2;
		int tb3 = (tb1+tb2)/2;
		
		outPixels[nindex] = (ta1 << 24) | (tr3 << 16) | (tg3 << 8) | tb3;
	}


	private void kZoom(int[] inPixels, int width, int height,BufferedImage dest) {
		
	}

	private BufferedImage pixelZoom(int[] inPixels, int width, int height,BufferedImage src,BufferedImage dest) {
		int nwidth = width * times;
		int nheight = height * times;
		int[] outPixels = new int[nwidth * nheight];
		int index = 0;
		for (int row = 0; row < nheight; row++) {
			for (int col = 0; col < nwidth; col++) {
				index = row * nwidth + col;
				int nrow = (int) (row / times);
				int ncol = (int) (col / times);
				int index2 = nrow * width + ncol;
				outPixels[index] = inPixels[index2];
			}
		}
		if(dest==null){
			dest = createCompatibleDestImage(src,nwidth,nheight,null);
		}
		setRGB(dest, 0, 0, nwidth, nheight, outPixels);
		return dest;
	}

}
