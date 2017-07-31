package com.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.net.SocketFactory;

public class Consumer {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, UnknownHostException, IOException, ClassNotFoundException {
		String interfacename = SayHelloService.class.getName();
		Method method = SayHelloService.class.getMethod("sayHello", java.lang.String.class);
		Object[] arguments = {"hellod"};
		System.out.println(method);
		System.out.println(Arrays.toString(method.getParameterTypes()));
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 12345);
		SocketFactory factory = SocketFactory.getDefault();
		Socket socket = factory.createSocket(address.getAddress(), address.getPort());
		
//		Socket socket = new Socket("127.0.0.1", 12345);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		
		output.writeUTF(interfacename);
		output.writeUTF(method.getName());
		output.writeObject(method.getParameterTypes());
		output.writeObject(arguments);
		
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		Object result = input.readObject();
		System.out.println(result);
	}
}
