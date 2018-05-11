package com.dnf.test;

import java.util.Random;

/*
 * 不限
android 2.*
android 3.*
android 4.*
android 5.*
android 6.*
android 7.*
android 8.*
 */
public class Version {
	
	static final String AD_VERSION = "test:ad:ver:";
	
	String [] str = {"all","android_2","android_3","android_4","android_5","android_6","android_7","android_8"};
	
	Random random = new Random();
	
	public String getValue() {
		int nextInt = random.nextInt(str.length);
		return AD_VERSION+str[nextInt];
	}
	
	public String getV() {
		int nextInt = random.nextInt(str.length);
		return str[nextInt];
	}
}
