package com.loadclass.classloader;

import java.io.IOException;

public class MyClassLoader extends ClassLoader {
	///Users/gbs/tool/git/java-other-test/target/classes/com/loadclass/classloader/ServiceB.class
	private static final String BASE_DIR = "/Users/gbs/tool/git/java-other-test/data/";

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String fileName = name.replaceAll("\\.", "/");
		fileName = BASE_DIR + fileName + ".class";
		System.out.println(fileName);
		try {
			byte[] bytes = BinaryFileUtils.readFileToByteArray(fileName);
			return defineClass(name, bytes, 0, bytes.length);
		} catch (IOException e) {
			throw new ClassNotFoundException("failed to load class " + name, e);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		String className = "shuo.laoma.dynamic.c87.HelloService";
		
		MyClassLoader cl1 = new MyClassLoader();
		Class<?> class1 = cl1.loadClass(className);

		MyClassLoader cl2 = new MyClassLoader();
		Class<?> class2 = cl2.loadClass(className);
		System.out.println(class1+"=="+class2);
		if (class1 != class2) {
			System.out.println("different classes");
		}
	}

}
