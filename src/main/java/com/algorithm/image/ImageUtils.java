package com.algorithm.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageUtils {

	/**
	 * 缩小图片尺寸
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param image
	 *            原来BufferedImage对象
	 * @param newWidth
	 *            要缩放的宽度
	 * @param newHeight
	 *            要缩放的高度
	 * @return BufferedImage
	 * @throws ImageException
	 */
	public static BufferedImage resize(BufferedImage image, int newWidth, int newHeight) throws ImageException {
		if (image.getWidth() < newWidth) {
			throw new ImageException("原始宽小于要缩放的宽");
		}

		if (image.getHeight() < newHeight) {
			throw new ImageException("原始高小于要缩放的高");
		}

		BufferedImage tmp = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = tmp.createGraphics();
		graphics.drawImage(image, 0, 0, newWidth, newHeight, null);
		return tmp;
	}

	/**
	 * 转化为灰色
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param image
	 * @param gray
	 * @return
	 */
	public static BufferedImage gray(BufferedImage image, ImageGray gray) {
		BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				final int color = image.getRGB(x, y);
				int newPixel = gray.getColor(ImageBaseColor.RED.getColor(color), ImageBaseColor.GREEN.getColor(color), ImageBaseColor.BLUE.getColor(color));
				grayImage.setRGB(x, y, newPixel);
			}
		}
		return grayImage;
	}

	/**
	 * 求平均值
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param tmp
	 * @param h
	 * @param w
	 * @return
	 */
	public static long avg(int[][] tmp, int h, int w) {
		long sum = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				sum += tmp[y][x];
			}
		}
		return sum / (w * h);
	}

	/**
	 * 平均值比较
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param tmp
	 * @param h
	 * @param w
	 * @param avg
	 * @return
	 */
	public static String compareAvg(int[][] tmp, int h, int w, long avg) {
		StringBuilder sb = new StringBuilder(h * w + 1);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (tmp[y][x] >= avg) {
					sb.append("1");
				} else {
					sb.append("0");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 二进制转换十六进制
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param str
	 * @return
	 */
	public static String toHix(String str) {
		int n = str.length() / 4;
		StringBuilder sb = new StringBuilder(n + 1);
		for (int i = 0; i < n; i++) {
			String substring = str.substring(i * 4, (i + 1) * 4);
			Integer valueOf = Integer.valueOf(substring, 2);
			String hexString = Integer.toHexString(valueOf.intValue());
			sb.append(hexString);
		}
		return sb.toString();
	}

	/**
	 * 转换二维数组
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param image
	 * @param w
	 *            为x横
	 * @param h
	 *            为y纵
	 * @return 二维数组
	 */
	public static int[][] toArray(BufferedImage image, int w, int h) {
		int[][] tmp = new int[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				final int color = image.getRGB(x, y);
				tmp[y][x] = ImageBaseColor.BLUE.getColor(color);
			}
		}
		return tmp;
	}

	public static void show(BufferedImage image) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				System.out.print(image.getRGB(x, y) + " ");
			}
			System.out.println();
		}
	}

	public static void show(int[][] tmp) {
		for (int y = 0; y < tmp.length; y++) {
			for (int x = 0; x < tmp[y].length; x++) {
				System.out.print(tmp[y][x] + " ");
			}
			System.out.println();
		}
	}

}
