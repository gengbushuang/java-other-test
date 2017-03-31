package com.rpc.proxy;

import java.io.IOException;
import java.lang.reflect.Proxy;


public class ProxyMain {

	public static void main(String []args){
//		System.out.println(c.getName());
//		XiangQinInterface interface1 = (XiangQinInterface) Proxy.newProxyInstance(c.getClassLoader(),new Class[] { c },new Invoker(c));
//		interface1.xiangQin();
		DefautRpcProxyFatory proxyFatory = new DefautRpcProxyFatory();
		try {
			 RpcProxy<XiangQinInterface> rpcProxy = proxyFatory.getProxy(XiangQinInterface.class, null, null);
			 XiangQinInterface proxy = rpcProxy.getProxy();
			 proxy.xiang("ddd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
