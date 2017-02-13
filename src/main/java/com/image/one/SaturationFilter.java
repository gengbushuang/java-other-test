package com.image.one;

import java.awt.image.BufferedImage;
/**
 * 饱和度
 * @Description:TODO
 * @author gbs
 * @date 2017年2月13日 下午10:42:13
 */
public class SaturationFilter extends AbstractBufferedImageOp {

	public final static double clo60 = 1.0 / 60.0;
	public final static double clo255 = 1.0 / 255.0;
	private double ratio = 0.25;

	public SaturationFilter(double ratio) {
		this.ratio = ratio;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();
		double sat = 127.0d * ratio;
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}

		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);

		double min, max, dif, sum;
		double f1, f2;
		int index = 0;
		double h, s, l;
		double v1, v2, v3, h1;
		for (int row = 0; row < height; row++) {
			int ta = 0, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				ta = (inPixels[index] >> 24) & 0xff;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;

				// convert to HSL space
				// 转换HSL色彩空间
				// 获取这个三个色最小值
				min = tr;
				if (tg < min) {
					min = tg;
				}
				if (tb < min) {
					min = tb;
				}
				// 如果红色是最大值，f1=0.0，f2=绿-蓝
				max = tr;
				f1 = 0.0;
				f2 = tg - tb;
				// 如果绿色是最大值，f1=120，f2=蓝-红
				if (tg > max) {
					max = tg;
					f1 = 120.0;
					f2 = tb - tr;
				}
				// 如果蓝色是最大，f1=240，f2=红-绿
				if (tb > max) {
					max = tb;
					f1 = 240.0;
					f2 = tr - tg;
				}

				dif = max - min;
				sum = max + min;
				l = 0.5 * sum;
				if (dif == 0) {
					h = 0.0;
					s = 0.0;
				} else if (l < 127.5) {
					s = 255.0 * dif / sum;
				} else {
					s = 255.0 * dif / (510.0 - sum);
				}

				h = (f1 + 60.0 * f2 / dif);
				if (h < 0.0) {
					h += 360.0;
				}
				if (h >= 360.0) {
					h -= 360.0;
				}
				s = s + sat;
				if (s < 0.0) {
					s = 0.0;
				}
				if (s > 255.0) {
					s = 255.0;
				}
				// 转换回RGB色彩空间
				if (s == 0) {
					tr = (int) l;
					tg = (int) l;
					tb = (int) l;
				} else {
					if (l < 127.5) {
						v2 = clo255 * l * (255 + s);
					} else {
						v2 = l + s - clo255 * s * l;
					}
					v1 = 2 * l - v2;
					v3 = v2 - v1;
					h1 = h + 120.0;
					if (h1 >= 360.0) {
						h1 -= 360.0;
					}
					if (h1 < 60.0) {
						tr = (int) (v1 + v3 * h1 * clo60);
					} else if (h1 < 180.0) {
						tr = (int) v2;
					} else if (h1 < 240.0) {
						tr = (int) (v1 + v3 * (4 - h1 * clo60));
					} else {
						tr = (int) v1;
					}
					h1 = h;
					if (h1 < 60.0) {
						tg = (int) (v1 + v3 * h1 * clo60);
					} else if (h1 < 180.0) {
						tg = (int) v2;
					} else if (h1 < 240.0) {
						tg = (int) (v1 + v3 * (4 - h1 * clo60));
					} else {
						tg = (int) v1;
					}

					h1 = h - 120.0;
					if (h1 < 0.0) {
						h1 += 360.0;
					}
					if (h1 < 60.0) {
						tb = (int) (v1 + v3 * h1 * clo60);
					} else if (h1 < 180.0) {
						tb = (int) v2;
					} else if (h1 < 240.0) {
						tb = (int) (v1 + v3 * (4 - h1 * clo60));
					} else {
						tb = (int) v1;
					}
				}
				outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
			}
		}
		setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}
}
