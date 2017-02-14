package com.image.four;

import java.awt.image.BufferedImage;
/**
 * 亮度调整
 * @Description:TODO
 * @author gbs
 * @date 2017年2月13日 下午11:19:41
 */
public class BrightFilter extends AbstractBufferedImageOp {

	private float brightness;

	public BrightFilter() {
		this(1.2f);
	}

	public BrightFilter(float bright) {
		this.brightness = bright;
	}

	public float getBrightness() {
		return brightness;
	}

	public void setBrightness(float brightness) {
		this.brightness = brightness;
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
		int sumR = 0, sumG = 0, sumB = 0;
		int total = width * height;
		int[] meansRGB = new int[3];
		for (int row = 0; row < height; row++) {
			int tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				sumR += tr;
				sumG += tg;
				sumB += tb;
			}
		}
		// 计算像素在R、G、B三个分量上的平均值means
		meansRGB[0] = sumR / total;
		meansRGB[1] = sumG / total;
		meansRGB[2] = sumB / total;

		for (int row = 0; row < height; row++) {
			int ta = 0, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				ta = (inPixels[index] >> 24) & 0xff;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				// 对每个像素值在R、G、B上的分量，减去计算出来的平均值means
				tr -= meansRGB[0];
				tg -= meansRGB[1];
				tb -= meansRGB[2];
				// 平均值分别乘以对应亮度系数的bfighthess，默认值为1表示亮度不变，大于1表示亮度提高，小于表示亮度降低
				// 调整图像亮度公式：Pnew=Pold+(bfighthess-1)*means
				int fr = (int) (tr + getBrightness() * meansRGB[0]);
				int fg = (int) (tg + getBrightness() * meansRGB[1]);
				int fb = (int) (tb + getBrightness() * meansRGB[2]);

				outPixels[index] = (ta << 24) | (clamp(fr) << 16) | (clamp(fg) << 8) | clamp(fb);
			}
		}
		setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}

	public static int clamp(int c) {
		return c > 255 ? 255 : (c < 0 ? 0 : c);
	}
}
