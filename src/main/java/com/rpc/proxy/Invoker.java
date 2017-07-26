package com.rpc.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.SocketFactory;

import com.rpc.message.ClassMsgCoder;

public class Invoker implements RpcInvocationHandler {

	private final Class<?> protocol;
	private final Socket socket;

	public Invoker(Class<?> _protocol, InetSocketAddress _addr, SocketFactory _factory) throws IOException {
		this.protocol = _protocol;
		if(_factory==null){
			_factory = SocketFactory.getDefault();
		}
		socket = _factory.createSocket(_addr.getAddress(), _addr.getPort());
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		ClassMsgCoder classMsgCoder = new ClassMsgCoder(method,args);
		
		// System.out.println(protocol.getName());
		// System.out.println(method.getName());
		// System.out.println(Arrays.toString(args));
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		output.writeUTF(protocol.getName());
		output.writeUTF(method.getName());
		output.writeObject(method.getParameterTypes());
		output.writeObject(args);

		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		Object result = input.readObject();

		return result;
	}

}
