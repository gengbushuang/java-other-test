package com.loadclass;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {

	public static void main(String[] args) throws Exception {
		ClassLoader myloader = new ClassLoader() {

			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				System.out.println("name-->"+name);
				try {
					String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
					System.out.println("fileName-->"+fileName);
					InputStream is = getClass().getResourceAsStream(fileName);
					if (is == null) {
						return super.loadClass(name);
					}
					System.out.println("-->"+fileName);
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
		};
		
		Object obj = myloader.loadClass("com.loadclass.ClassLoaderTest").newInstance();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof com.loadclass.ClassLoaderTest);
	}
}
