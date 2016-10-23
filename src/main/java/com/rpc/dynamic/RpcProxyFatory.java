package com.rpc.dynamic;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.SocketFactory;


public interface RpcProxyFatory{


	<T> RpcProxy<T> getProxy(Class<T> protocol, InetSocketAddress addr, SocketFactory factory) throws IOException;


}
