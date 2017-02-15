package com.image.one;

import java.util.ArrayList;
import java.util.List;

public class PixelMeanShift {

	public int meanShift(int[] inPixels) {
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
