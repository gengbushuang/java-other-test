package com.image.six.search;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.algorithm.image.ImageException;
import com.algorithm.image.ImageUtils;

public class SearchMain {
	

	public static void main(String[] args) throws IOException, ImageException {
		SearchSimilar similar = new SearchSimilar();
		SearchFilter filter = new SearchFilter();
		String path1 = "/Users/gbs/tmp/p1.jpg";
		BufferedImage image1 = ImageIO.read(new File(path1));
		
		String path2 = "/Users/gbs/tmp/p2.jpg";
		BufferedImage image2 = ImageIO.read(new File(path2));
		
		int width = Math.min(image1.getWidth(), image2.getWidth());
		int height = Math.min(image1.getHeight(), image2.getHeight());
		
		image1 = ImageUtils.resize(image1, width, height);
		image2 = ImageUtils.resize(image2, width, height);
		
		
		double[] ds1 = filter.get(image1);
		double[] ds2 = filter.get(image2);
		
		double d1 = similar.euclid(ds1, ds2);
		System.out.println(d1);
		
		double d2 = similar.bhattacharyyaCoefficient(ds1, ds2);
		System.out.println(d2);
	}
}
