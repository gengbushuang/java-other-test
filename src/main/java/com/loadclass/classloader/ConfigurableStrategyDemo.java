package com.loadclass.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 类加载的应用:可配置的策略
 * 
 * @author gbs
 *
 */
public class ConfigurableStrategyDemo {

	public static IService createService() {
		try {
			Properties prop = new Properties();
			//System.out.println(System.getProperty("user.dir"));
			String fileName = "/com/loadclass/classloader/config.properties";
			fileName = System.getProperty("user.dir")+"/src/main/java/com/loadclass/classloader/config.properties";
			prop.load(new FileInputStream(new File(fileName)));
			String className = prop.getProperty("service");
			Class<?> cls = Class.forName(className);
			return (IService) cls.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		IService service = createService();
		service.action();
	}
}
