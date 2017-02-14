package com.image.three;

import java.awt.image.BufferedImage;

/**
 * 怀旧风格图片
 * @Description:TODO
 * @author gbs
 * @date 2017年2月12日 下午9:45:48
 */
public class SepiaToneFilter extends AbstractBufferedImageOp {

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
		for (int row = 0; row < height; row++) {
			int ta = 0, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				ta = (inPixels[index] >> 24) & 0xff;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				int fr = (int) colorBlend(noise(), (tr * 0.393) + (tg * 0.769) + (tb * 0.189), tr);
				int fg = (int) colorBlend(noise(), (tr * 0.349) + (tg * 0.686) + (tb * 0.168), tg);
				int fb = (int) colorBlend(noise(), (tr * 0.272) + (tg * 0.534) + (tb * 0.131), tb);

				outPixels[index] = (ta << 24) | (clamp(fr) << 16) | (clamp(fg) << 8) | clamp(fb);
			}
		}
		setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}

	/*
	 * 获取混合的权重系数，通过随机方法获取，取值范围为[0~1]
	 */
	private double noise() {
		return Math.random() * 0.5 + 0.5;
	}

	/*
	 * 根据权重系数，将该像素点的原值与第一步中得到的新值混合，从而得到该像素最终值
	 */
	private double colorBlend(double scale, double dest, double src) {
		return (scale * dest + (1.0 - scale) * src);
	}

	public static int clamp(int c) {
		return c > 255 ? 255 : (c < 0 ? 0 : c);
	}
}
