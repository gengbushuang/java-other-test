package com.rpc.proxy;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

import javax.net.SocketFactory;

public class DefautRpcProxyFatory implements RpcProxyFatory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> RpcProxy<T> getProxy(Class<T> protocol, InetSocketAddress addr, SocketFactory factory) throws IOException {
		Invoker invoker = new Invoker(protocol,addr,factory);
		T t = (T) Proxy.newProxyInstance(protocol.getClassLoader(), new Class[] { protocol }, invoker);
		return new RpcProxy<T>(protocol, t);
	}

}
