package com.rpc.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.SocketFactory;

public interface RpcProxyFatory {

	public <T> RpcProxy<T> getProxy(Class<T> protocol, InetSocketAddress addr, SocketFactory factory) throws IOException;
}
