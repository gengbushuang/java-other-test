package com.rpc.proxy;

import java.lang.reflect.Method;


public class Invoker implements RpcInvocationHandler{
	
	private final Class<?> protocol;
	
	public Invoker(Class<?> protocol){
		this.protocol = protocol;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		 System.out.println(protocol.getName());  
		 System.out.println(method.getName());  
		 
		return null;
	}

}
