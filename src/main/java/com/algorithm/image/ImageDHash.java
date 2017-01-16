package com.algorithm.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 感知哈希算法 参见:imgHash.py
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年1月16日 下午2:02:03
 */
public class ImageDHash {

	public void avhash(BufferedImage image) throws ImageException {
		// 第一步，缩小尺寸,8*8
		BufferedImage bufferedImage = ImageUtils.resize(image, 8, 8);
		// 第二步，简化色彩-将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		BufferedImage grayImage = ImageUtils.gray(bufferedImage, ImageGray.WEIGHTED);
		int[][] array = ImageUtils.toArray(grayImage, grayImage.getWidth(), grayImage.getHeight());
		ImageUtils.show(array);
		// 第三步，计算平均值。计算所有64个像素的灰度平均值。
		long avg = ImageUtils.avg(array, array.length, array[0].length);
		System.out.println(avg);
		// 第四步，比较像素的灰度。将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		String compareAvg = ImageUtils.compareAvg(array, array.length, array[0].length, avg);
		System.out.println(compareAvg);
		// 第五步，计算哈希值。将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
		String hix = ImageUtils.toHix(compareAvg);
		System.out.println(hix);
	}

	public static void main(String[] args) throws IOException, ImageException {
		ImageDHash dHash = new ImageDHash();
		BufferedImage bufferedImage = ImageIO.read(new File("/Users/gbs/tmp/tm.jpg"));
		dHash.avhash(bufferedImage);
	}
}
