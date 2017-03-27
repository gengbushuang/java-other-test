package com.image.four;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Hashtable;

public abstract class AbstractBufferedImageOp {

	public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		// 颜色类别
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_BGR) {
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		} else {
			return image.getRGB(x, y, width, height, pixels, 0, width);
		}
	}

	public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
			image.getRaster().setDataElements(x, y, width, height, pixels);
		} else {
			image.setRGB(x, y, width, height, pixels, 0, width);
		}
	}

	public BufferedImage createCompatibleDestImage(BufferedImage src, Hashtable<?, ?> properties) {
		ColorModel cm = src.getColorModel();
		BufferedImage image = new BufferedImage(cm, cm.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), cm.isAlphaPremultiplied(), properties);
		return image;
	}
	
	public int clamp(int c) {
		return c > 255 ? 255 : (c < 0 ? 0 : c);
	}

	public abstract BufferedImage filter(BufferedImage src, BufferedImage dest);
}
