package com.image.six;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年2月16日 上午9:15:16
 */
public class PixelMeanShift {

	/*
	 * 给定一个初始化阀值T(可以随机生成，或者直接为127)
	 * 根据像素值P(x,y)与阀值T的比较结果，将其分为对象像素集合G1与背景像素集合G2
	 * 计算G1与G2的像素平均值M1、M2
	 * 得到新的阀值T'=(M1+M2)/2
	 * 比较T'与T是否相等，如果不等，则令T=T'，重复以上第二条到第五条
	 * 最终得到T值作为阀值，像素值大于T即为白色，反之黑色
	 */
	public int meanShift(int[] inPixels) {
		//
		int itithreshold = 127;
		int finalthreshold = 0;
		int[] tmp = new int[inPixels.length];
		for (int i = 0; i < inPixels.length; i++) {
			tmp[i] = (inPixels[i] >> 16) & 0xff;
		}
		
		List<Integer> g1 = new ArrayList<Integer>();
		List<Integer> g2 = new ArrayList<Integer>();
		while (finalthreshold != itithreshold) {
			finalthreshold = itithreshold;
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i] > itithreshold) {
					g2.add(tmp[i]);
				} else {
					g1.add(tmp[i]);
				}
			}
			int m1 = mean(g1);
			int m2 = mean(g2);
			g1.clear();
			g2.clear();
			itithreshold = (m1 + m2) / 2;
		}
		return itithreshold;
	}

	private int mean(List<Integer> g) {
		int sum = 0;
		for (int i : g) {
			sum += i;
		}
		return sum / g.size();
	}
}
