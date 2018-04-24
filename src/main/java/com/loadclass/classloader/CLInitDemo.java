package com.loadclass.classloader;

public class CLInitDemo {

	public static class Hello {
		static{
			System.out.println("hello");
		}
	}
	
	public static void main(String[] args) {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		String className = CLInitDemo.class.getName()+"$Hello";
		
		try {
			//类被加载了，没有任何输出，static语句块没有被执行
			Class<?> cls = cl.loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/////////
		try {
			//这个可以输出static的语句块
			Class<?> forName = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
