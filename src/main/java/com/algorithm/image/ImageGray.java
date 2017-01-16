package com.algorithm.image;

/**
 * 
 * 24位彩色图与8位灰度图 首先要先介绍一下24位彩色图像，在一个24位彩色图像中，每个像素由三个字节表示，通常表示为RGB。
 * 通常，许多24位彩色图像存储为32位图像，每个像素多余的字节存储为一个alpha值，表现有特殊影响的信息[1]。
 * 在RGB模型中，如果R=G=B时，则彩色表示一种灰度颜色，其中R=G=B的值叫灰度值，因此，灰度图像每个像素只需一个字节存放灰度值（又称强度值、亮度值
 * ），灰度范围为0-255[2]。 这样就得到一幅图片的灰度图。 几种灰度化的方法 1、分量法：使用RGB三个分量中的一个作为灰度图的灰度值。
 * 2、最值法：使用RGB三个分量中最大值或最小值作为灰度图的灰度值。 3、均值法：使用RGB三个分量的平均值作为灰度图的灰度值。
 * 4、加权法：由于人眼颜色敏感度不同，按下一定的权值对RGB三分量进行加权平均能得到较合理的灰度图像。 一般情况按照：Y = 0.30R + 0.59G +
 * 0.11B。 [注]加权法实际上是取一幅图片的亮度值作为灰度值来计算，用到了YUV模型。 在[3]中会发现作者使用了Y = 0.21 * r + 0.71
 * * g + 0.07 * b来计算灰度值（显然三个权值相加并不等于1，可能是作者的错误？）。 实际上，这种差别应该与是否使用伽马校正有关[1]。
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年1月16日 上午10:47:48
 */
public enum ImageGray {

	WEIGHTED {
		@Override
		public int getColor(int r, int g, int b) {
			int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
			return colorToRGB(255, gray, gray, gray);
		}

		private int colorToRGB(int alpha, int red, int green, int blue) {
			int newPixel = 0;
			newPixel += alpha;
			newPixel = newPixel << 8;
			newPixel += red;
			newPixel = newPixel << 8;
			newPixel += green;
			newPixel = newPixel << 8;
			newPixel += blue;
			return newPixel;
		}
	};

	public abstract int getColor(int r, int g, int b);
}
