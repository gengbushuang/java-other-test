package com.dnf.test;

import java.util.Random;

public class GroupUser {
	
	static final String AD_GROUP = "test:ad:group:";
	
	String[] str = { "MOSLEMNO", "MOSLEMYES" };

	Random random = new Random();

	public String getValue() {
		int nextInt = random.nextInt(str.length);
		return AD_GROUP+str[nextInt];
	}
}
