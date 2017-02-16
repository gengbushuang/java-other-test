package com.image.six;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.image.four.AbstractBufferedImageOp;

/**
 * 图像直方图
 * @Description:TODO
 * @author gbs
 * @Date 2017年2月16日 下午5:53:42
 */
public class RGBHistogramFilter extends AbstractBufferedImageOp {

	public RGBHistogramFilter() {
		System.out.println("Colorful Histogram");
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();
		if (dest == null) {
			dest = createCompatibleDestImage(src, null);
		}

		int[] inPixels = new int[width * height];
//		int[] outPixels = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);
		int[][] histogram = new int[3][256];
		for (int i = 0; i < histogram.length; i++) {
			Arrays.fill(histogram[i], 0);
		}
		int index = 0;
		for (int row = 0; row < height; row++) {
			int tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				histogram[0][tr]++;
				histogram[1][tg]++;
				histogram[2][tb]++;
			}
		}
		double[] maxRGBFrequency = { 0, 0, 0 };
		for (int i = 0; i < histogram[0].length; i++) {
			maxRGBFrequency[0] = Math.max(maxRGBFrequency[0], histogram[0][i]);
			maxRGBFrequency[1] = Math.max(maxRGBFrequency[1], histogram[1][i]);
			maxRGBFrequency[2] = Math.max(maxRGBFrequency[2], histogram[2][i]);
		}

		Graphics2D g2d = dest.createGraphics();
		g2d.setPaint(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, width, height);
		double max = Math.max(maxRGBFrequency[0], Math.max(maxRGBFrequency[1], maxRGBFrequency[2]));

		g2d.setPaint(Color.BLACK);
		g2d.drawLine(50, 50, 50, height - 50);
		g2d.drawLine(50, height - 50, width - 50, height - 50);

		g2d.drawString("0", 50, height - 30);
		g2d.drawString("255", width - 50, height - 30);
		g2d.drawString("0", 20, height - 50);
		g2d.drawString("" + max, 20, 50);

		double xunit = (width - 100.0) / 256.0d;
		double yunit = (height - 100.0) / max;
		g2d.setPaint(Color.RED);
		for (int i = 0; i < histogram[0].length; i++) {
			double xp = 50 + xunit * i;
			double yp = yunit * histogram[0][i];
			Rectangle2D rect2d = new Rectangle2D.Double(xp, height - 50 - yp, xunit, yp);
			g2d.fill(rect2d);
		}
		g2d.setPaint(Color.GREEN);
		for (int i = 0; i < histogram[1].length; i++) {
			double xp = 50 + xunit * i;
			double yp = yunit * histogram[1][i];
			Rectangle2D rect2d = new Rectangle2D.Double(xp, height - 50 - yp, xunit, yp);
			g2d.fill(rect2d);
		}
		g2d.setPaint(Color.BLUE);
		for (int i = 0; i < histogram[2].length; i++) {
			double xp = 50 + xunit * i;
			double yp = yunit * histogram[2][i];
			Rectangle2D rect2d = new Rectangle2D.Double(xp, height - 50 - yp, xunit, yp);
			g2d.fill(rect2d);
		}

		return dest;
	}

	public static void main(String[] args) {

	}
}
