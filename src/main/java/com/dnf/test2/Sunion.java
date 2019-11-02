package com.dnf.test2;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Sunion {

	public int[] sunion(int[] a, int[] b) {
		int length = Math.max(a.length, b.length);
		int[] tmp = new int[length];

		int i = 0;
		int j = 0;
		int k = 0;
		for (; i < a.length && j < b.length;) {
			if (a[i] < b[j]) {
				i++;
				continue;
			}
			if (a[i] > b[j]) {
				j++;
				continue;
			}
			if (a[i] == b[j]) {
				tmp[k++] = a[i];
				i++;
				j++;
			}
		}
		
		int[] duplicate = new int[k];
		System.arraycopy(tmp, 0, duplicate, 0, k);
		return duplicate;
	}
	
	Random r = new Random();
	public int [] create(final int n){
		int[] array = Stream.iterate(0,x->r.nextInt(n)).limit(n).mapToInt(i->i).sorted().distinct().toArray();
		return array;
	}
	
	public static void main(String[] args) {
		Sunion sunion = new Sunion();
		int[] a = sunion.create(5000);
		//System.out.println(Arrays.toString(a));
		
		int[] b = sunion.create(5000);
		//System.out.println(Arrays.toString(b));
		long n = System.currentTimeMillis();
		int[] sunion2 = sunion.sunion(a, b);
		System.out.println(System.currentTimeMillis()-n);
		int[] c = sunion.create(5000);
		System.out.println(Arrays.toString(sunion2));
		
		n = System.currentTimeMillis();
		int[] sun = sunion.sunion(sunion2, c);
		System.out.println(System.currentTimeMillis()-n);
		System.out.println(Arrays.toString(sun));
	}
}
