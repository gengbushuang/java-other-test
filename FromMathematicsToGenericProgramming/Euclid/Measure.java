package Euclid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

/**
 * 最大公度量
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年11月13日 下午2:34:42
 */
public class Measure {
	// 毕式学派最大公度量GCM
	// gcm(a,a)=a
	// gcm(a,b)=gcm(a,a+b)
	// b<a=>gcm(a,b)=gcm(a-b,b)
	// gcm(a,b)=gcm(b,a)
	public int gcm(int a, int b) {
		if (a == b) {
			return a;
		} else if (b < a) {
			return gcm(a - b, b);
		} else {
			return gcm(a, b - a);
		}
	}

	public int gcm0(int a, int b) {
		int tmpa = a;
		int tmpb = b;
		while (tmpa != tmpb) {
			if (tmpb < tmpa) {
				tmpa = tmpa - tmpb;
			} else {
				tmpb = tmpb - tmpa;
			}
		}
		return tmpa;
	}

	public int gcm1(int a, int b) {
		int tmpa = a;
		int tmpb = b;
		int tmp = 0;
		while (tmpa != tmpb) {
			// 修改
			// if (tmpb < tmpa) {
			// tmpa = dataDifference(tmpb,tmpa);
			// } else {
			// tmpb = dataDifference(tmpa,tmpb);
			// }
			tmpb = dataDifference(tmpa, tmpb);

			tmp = tmpa;
			tmpa = tmpb;
			tmpb = tmp;
		}
		return tmpb;
	}

	public int dataDifference(int a, int b) {
		while (a < b) {
			b = b - a;
		}
		return b;
	}

	public static void main(String[] args) {
		// Measure measure = new Measure();
		// int gcm = measure.gcm1(196, 42);
		// System.out.println(gcm);
		int[] a = { 1, 2, 3, 4, 5 };
		List<Integer> asList = Arrays.asList(ArrayUtils.toObject(a));
		System.out.println(asList);
	}
}
