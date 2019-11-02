package com.dnf.model;

import java.util.Random;

public class Age {

	static final String AD_AGE = "test:ad:age:";
	
	String[] str = { "ALL", "JUVENILE", "YOUTH", "ADULT" };

	Random random = new Random();

	public String getValue() {
		int nextInt = random.nextInt(str.length);
		return AD_AGE+str[nextInt];
	}
}
