package com.rpc.proxy;

import java.lang.reflect.Proxy;


public class ProxyMain {

	public static void main(String []args){
		Class<?> c = XiangQinInterface.class;
		System.out.println(c.getName());
		XiangQinInterface interface1 = (XiangQinInterface) Proxy.newProxyInstance(c.getClassLoader(),new Class[] { c },new Invoker(c));
		interface1.xiangQin();
	}
}
