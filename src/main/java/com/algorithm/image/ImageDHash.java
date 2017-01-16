package com.algorithm.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageDHash {

	public void avhash(BufferedImage image) throws ImageException {
		// 第一步，缩小尺寸,9*8
		BufferedImage bufferedImage = ImageUtils.resize(image, 9, 8);
		// 第二步，简化色彩-将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		BufferedImage grayImage = ImageUtils.gray(bufferedImage, ImageGray.WEIGHTED);
		int[][] array = ImageUtils.toArray(grayImage, grayImage.getWidth(), grayImage.getHeight());
//		ImageUtils.show(array);
		StringBuilder sb = new StringBuilder(grayImage.getWidth()*grayImage.getHeight());
		for(int y=0;y<array.length;y++){
			for(int x=0;x<array[y].length-1;x++){
				if(array[y][x]>=array[y][x+1]){
					sb.append("1");
				}else{
					sb.append("0");
				}
			}
		}
//		System.out.println(sb.toString());
		String hix = ImageUtils.toHix(sb.toString());
		System.out.println(hix);
	}
	
	public static void main(String[] args) throws IOException, ImageException {
		ImageDHash dHash = new ImageDHash();
		BufferedImage bufferedImage = ImageIO.read(new File("F:/tmp/IMG_1182.JPG"));
		dHash.avhash(bufferedImage);
	}
}
