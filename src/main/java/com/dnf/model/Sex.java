package com.dnf.model;

import java.util.Random;

public class Sex {
	static final String AD_SEX = "test:ad:sex:";
	String[] str = { "ALL", "MAN", "WOMAN" };

	Random random = new Random();

	public String getValue() {
		int nextInt = random.nextInt(str.length);
		return AD_SEX+str[nextInt];
	}
	
	public static void main(String[] args) {
		Sex s = new Sex();
		for(int i = 0; i< 10;i++) {
			System.out.println(s.getValue());
		}
	}
}
