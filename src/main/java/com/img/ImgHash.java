package com.img;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 参见imgHash.py
 * 
 * @Description:TODO
 * @author gbs
 * @date 2017年1月10日 下午11:32:04
 */
public class ImgHash {

	public void avhash(BufferedImage image) {
		// 第一步，缩小尺寸,8*8
		BufferedImage bufferedImage = resize(image, 8, 8);
		// 第二步，简化色彩-将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		BufferedImage grayImage = gray(bufferedImage);
		// 第三步，计算平均值。计算所有64个像素的灰度平均值。
		long avg = avg(grayImage);
		// 第四步，比较像素的灰度。将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		String compareAvg = compareAvg(grayImage, avg);
		// 第五步，计算哈希值。将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
		String hashValue = createHash(compareAvg);
		System.out.println(hashValue);
	}

	private String createHash(String compareAvg) {
		StringBuilder sb = new StringBuilder(17);
		for (int i = 0, n = compareAvg.length() / 4; i < n; i++) {
			String substring = compareAvg.substring(i * 4, (i + 1) * 4);
			Integer valueOf = Integer.valueOf(substring, 2);
			String hexString = Integer.toHexString(valueOf.intValue());
			sb.append(hexString);
		}
		return sb.toString();
	}

	private String compareAvg(BufferedImage bufferedImage, long avg) {
		StringBuilder sb = new StringBuilder(65);
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				if ((bufferedImage.getRGB(i, j) & 0xff) >= avg) {
					sb.append("1");
				} else {
					sb.append("0");
				}
			}
		}
		return sb.toString();
	}

	private long avg(BufferedImage bufferedImage) {
		long sum = 0;
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				sum += (bufferedImage.getRGB(i, j) & 0xff);
			}
		}
		return (sum / 64);
	}

	/**
	 * 24位彩色图与8位灰度图 首先要先介绍一下24位彩色图像，在一个24位彩色图像中，每个像素由三个字节表示，通常表示为RGB。
	 * 通常，许多24位彩色图像存储为32位图像，每个像素多余的字节存储为一个alpha值，表现有特殊影响的信息[1]。
	 * 在RGB模型中，如果R=G=B时，
	 * 则彩色表示一种灰度颜色，其中R=G=B的值叫灰度值，因此，灰度图像每个像素只需一个字节存放灰度值（又称强度值、亮度值
	 * ），灰度范围为0-255[2]。 这样就得到一幅图片的灰度图。 几种灰度化的方法 1、分量法：使用RGB三个分量中的一个作为灰度图的灰度值。
	 * 2、最值法：使用RGB三个分量中最大值或最小值作为灰度图的灰度值。 3、均值法：使用RGB三个分量的平均值作为灰度图的灰度值。
	 * 4、加权法：由于人眼颜色敏感度不同，按下一定的权值对RGB三分量进行加权平均能得到较合理的灰度图像。 一般情况按照：Y = 0.30R +
	 * 0.59G + 0.11B。 [注]加权法实际上是取一幅图片的亮度值作为灰度值来计算，用到了YUV模型。 在[3]中会发现作者使用了Y =
	 * 0.21 * r + 0.71 * g + 0.07 * b来计算灰度值（显然三个权值相加并不等于1，可能是作者的错误？）。
	 * 实际上，这种差别应该与是否使用伽马校正有关[1]。
	 * 
	 * @Description:TODO
	 * @author gbs
	 * @param bufferedImage
	 * @return
	 */
	private BufferedImage gray(BufferedImage bufferedImage) {
		// Java实现加权法灰度化
		BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				final int color = bufferedImage.getRGB(i, j);
				final int r = (color >> 16) & 0xff;
				final int g = (color >> 8) & 0xff;
				final int b = color & 0xff;
				int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
				// System.out.println(i + " : " + j + " " + gray);
				int newPixel = colorToRGB(255, gray, gray, gray);
				grayImage.setRGB(i, j, newPixel);
			}
		}
		return grayImage;
	}

	private static int colorToRGB(int alpha, int red, int green, int blue) {
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

	private BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
		BufferedImage tmp = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
		Graphics graphics = tmp.createGraphics();
		graphics.drawImage(image, 0, 0, newWidth, newHeight, null);
		return tmp;
	}

	// -------------------------------------

	public BufferedImage ContentCharacteristic(BufferedImage image) throws FileNotFoundException, IOException {
		BufferedImage bufferedImage = resize(image, 50, 50);
		BufferedImage grayImage = gray(bufferedImage);
		int thresholder = otsuThresholder(grayImage);
		BufferedImage test = test(thresholder, grayImage);
		return test;
	}

	private BufferedImage test(int thresholder, BufferedImage grayImage) {
		int width = grayImage.getWidth();
		int height = grayImage.getHeight();
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = grayImage.getRGB(i, j);
				int h = 0xFF & rgb;
				int newPixel = 0;
				if (h >= thresholder) {
					newPixel = 16777215;
				}
				tmp.setRGB(i, j, newPixel);
			}
		}
		return tmp;
	}

	/**
	 * otsu大律法
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param grayImage
	 * @return
	 */
	private int otsuThresholder(BufferedImage grayImage) {
		int width = grayImage.getWidth();
		int height = grayImage.getHeight();
		int ptr = 0;
		// 生成直方图数据
		int[] histData = new int[256];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = grayImage.getRGB(i, j);
				int h = 0xFF & rgb;
				// System.out.println(rgb+"==="+h);
				histData[h]++;
				ptr++;
			}
		}
		int total = ptr;
		System.out.println("ptr=" + ptr);
		//
		float sum = 0;
		for (int t = 0; t < 256; t++) {
			sum += t * histData[t];
			// System.out.println(t + "=" + histData[t]);
		}
		System.out.println("float sum=" + sum);

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		int threshold = 0;

		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Weight Background
			if (wB == 0) {
				continue;
			}

			wF = total - wB; // Weight Foreground
			if (wF == 0) {
				break;
			}

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB; // Mean Background
			float mF = (sum - sumB) / wF; // Mean Foreground
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
			// System.out.println(t + "=" +
			// varBetween+","+varMax+","+histData[threshold]);
		}
		return threshold;
	}

	public static void main(String[] args) throws IOException {
		ImgHash imgHash = new ImgHash();
		BufferedImage bufferedImage = ImageIO.read(new File("/Users/gbs/tmp/rt"));
//		imgHash.avhash(bufferedImage);
		BufferedImage image = imgHash.ContentCharacteristic(bufferedImage);
		ImageIO.write(image, "gif", new FileOutputStream(new File("/Users/gbs/tmp/rt_2")));
	}
}
