package com.algorithm.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;

import javax.imageio.ImageIO;
/**
 * otsu大律法 内容特征法
 * @Description:TODO
 * @author gbs
 * @Date 2017年1月16日 下午5:32:21
 */
public class ImageOtsuThresholder {

	public BitSet avhash(BufferedImage image) throws ImageException {
		BufferedImage bufferedImage = ImageUtils.resize(image, image.getWidth(), image.getHeight());
		BufferedImage grayImage = ImageUtils.gray(bufferedImage, ImageGray.WEIGHTED);
		int threshold = otsuThresholder(grayImage);
		System.out.println(threshold);
		int width = grayImage.getWidth();
		int height = grayImage.getHeight();
		BitSet bitSet = new BitSet(width * height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = grayImage.getRGB(x, y);
				int h = 0xFF & rgb;
				int index = y*width+x;
				if (h >= threshold) {
					bufferedImage.setRGB(x, y, (255<<24)|(255<<16)|(255<<8)|255);
//					System.out.print(1+" ");
					bitSet.set(index,true);
				} else {
					bufferedImage.setRGB(x, y, (255<<24)|(0<<16)|(0<<8)|0);
//					System.out.print(0+" ");
					bitSet.set(index,false);
				}

			}
//			System.out.println();
		}
		try {
			ImageIO.write(bufferedImage, "jpg", new File("F:/tmp/mmmm.JPG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitSet;
	}

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
		//
		float sum = 0;
		for (int t = 0; t < 256; t++) {
			sum += t * histData[t];
			// System.out.println(t + "=" + histData[t]);
		}

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

	public static void main(String[] args) throws IOException, ImageException {
		ImageOtsuThresholder otsuThresholder = new ImageOtsuThresholder();
		BufferedImage bufferedImage = ImageIO.read(new File("F:/tmp/IMG_1182.JPG"));
		BitSet bitSet = otsuThresholder.avhash(bufferedImage);
//		BufferedImage bufferedImage2 = ImageIO.read(new File("F:/tmp/IMG_1182.JPG"));
//		BitSet bitSet2 = otsuThresholder.avhash(bufferedImage2);
//		
//		bitSet.xor(bitSet2);
//		
//		int count = 0;
//		for(int i =0;i<bitSet.size();i++){
//			if(bitSet.get(i)){
//				count++;
//			}
//		}
//		System.out.println(count);
		
	}
}
