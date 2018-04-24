package com.loadclass.classloader;

public class ClassLoaderDemo {

	// sun.misc.Launcher$AppClassLoader 应用程序类加载器
	// sun.misc.Launcher$ExtClassLoader 扩展类加载器
	// null Bootstrap ClassLoader返回指为null
	public static void main(String[] args) {
		ClassLoader cl = ClassLoaderDemo.class.getClassLoader();
		while (cl != null) {
			System.out.println(cl.getClass().getName());
			// 获取父ClassLoader
			cl = cl.getParent();
		}
		System.out.println(String.class.getClassLoader());
		// //////下面这个可以获取默认的系统类加载器(测试了下获取不到，用的是1.8版本)
		cl = ClassLoader.getSystemClassLoader();
		try {
			Class<?> cls = cl.loadClass("java.util.ArrayList");
			ClassLoader actualLoader = cls.getClassLoader();
			System.out.println(actualLoader);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
