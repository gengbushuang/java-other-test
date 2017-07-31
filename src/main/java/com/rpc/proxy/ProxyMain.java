package com.rpc.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.SocketFactory;

import com.rpc.SayHelloService;


public class ProxyMain {

	public static void main(String []args){
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 12345);
		DefautRpcProxyFatory proxyFatory = new DefautRpcProxyFatory();
		try {
			 RpcProxy<SayHelloService> rpcProxy = proxyFatory.getProxy(SayHelloService.class, address, SocketFactory.getDefault());
			 SayHelloService proxy = rpcProxy.getProxy();
			 String sayHello = proxy.sayHello("高高挂");
			 System.out.println("-->"+sayHello);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
