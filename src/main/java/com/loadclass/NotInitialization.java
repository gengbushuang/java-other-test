package com.loadclass;

/**
 * -XX:+TraceClassLoading
 * @author gbs
 *
 */
public class NotInitialization {

	
	public static void main(String[] args) {
		System.out.println(SubClass.class);
//		SuperClass [] classes = new SuperClass[10];
	}
}
