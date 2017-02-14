package com.image.four;

import java.awt.image.BufferedImage;

/**
 * 图像对比
 * @Description:TODO
 * @author gbs
 * @date 2017年2月14日 下午9:54:31
 */
public class ContrastFilter extends AbstractBufferedImageOp {

	private float contrast;

	public ContrastFilter() {
		this(0.0f);
	}

	public ContrastFilter(float c) {
		this.contrast = c;
	}

	public float getContrast() {
		return contrast;
	}

	public void setContrast(float contrast) {
		this.contrast = contrast;
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
		// 对比系数，其取值范围为[0~2]
		if (this.contrast > 100) {
			this.contrast = 100;
		} else if (this.contrast < -100) {
			this.contrast = -100;
		}

		this.contrast = (1 + this.contrast / 100);
		for (int row = 0; row < height; row++) {
			int ta = 0, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				ta = (inPixels[index] >> 24) & 0xff;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				// 读取每个RGB像素值Prgb，Crgb=Prgb/255，使其值范围为[0~1]
				// 基于上一步计算结果((Crgb-0.5)*contrast+0.5)*255
				// 上一步结果就是处理后的像素值
				float fr = (tr / 255.0f) - 0.5f;
				float fg = (tg / 255.0f) - 0.5f;
				float fb = (tb / 255.0f) - 0.5f;

				tr = (int) ((fr * contrast + 0.5) * 255);
				tg = (int) ((fg * contrast + 0.5) * 255);
				tb = (int) ((fb * contrast + 0.5) * 255);

				outPixels[index] = (ta << 24) | (clamp(tr) << 16) | (clamp(tg) << 8) | clamp(tb);
			}
		}
		setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}

	public static int clamp(int c) {
		return c > 255 ? 255 : (c < 0 ? 0 : c);
	}

}
