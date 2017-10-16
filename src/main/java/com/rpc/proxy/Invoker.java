package com.rpc.proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.SocketFactory;

import com.rpc.message.ClassMsgCoder;
import com.rpc.message.ObjectMsgCoder;

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
		
		OutputStream outputStream = socket.getOutputStream();
		classMsgCoder.write(new DataOutputStream(outputStream));

		DataInputStream dis = new DataInputStream(socket.getInputStream());
		Object readObject = ObjectMsgCoder.readObject(dis);

		return readObject;
	}

}
