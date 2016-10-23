package com.rpc.dynamic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

import javax.net.SocketFactory;


public class RpcHandler implements RpcProxyFatory{

	

	@Override
	@SuppressWarnings("unchecked")
	public <T>  RpcProxy<T> getProxy(Class<T> protocol, InetSocketAddress addr, SocketFactory factory) throws IOException {
		Invoker handler = new Invoker(protocol,addr,factory);
		T proxy = (T) Proxy.newProxyInstance(protocol.getClassLoader(), new Class[] { protocol }, handler);
		return new RpcProxy<T>(protocol, proxy);
	}
	
	
	private static class Invocation {
		private String methodName;
		private Class<?>[] parameterClasses;
		private Object[] parameters;

		public Invocation(Method method, Object[] parameters) {
			// 方法名
			this.methodName = method.getName();
			// 方法参数的类型集合
			this.parameterClasses = method.getParameterTypes();
			// 要传入的参数
			this.parameters = parameters;
		}
		
		 public void readFields(DataInput in) throws IOException {
			 
		 }
		 
		 public void write(DataOutput out) throws IOException {
			 
		 }
	}
	
	private static class Invoker implements InvocationHandler{

		private RpcClient client;

		public Invoker(Class<?> protocol, InetSocketAddress addr, SocketFactory factory) {
			
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			
			//method要调用的方法,args方法要传入的参数
			new Invocation(method, args);
			return null;
		}
		
	}

}
