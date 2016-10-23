package com.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Provider {

	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ServerSocket server = new ServerSocket(12345);
		while(true){
			Socket socket = server.accept();
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			String interfacename = input.readUTF();
			String methodName = input.readUTF();
			Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
			Object [] arguments = (Object[]) input.readObject();
			
			Class serviceinterfaceclass = Class.forName(interfacename);
			Object service = new SayHelloServiceImpl();
			Method method = serviceinterfaceclass.getMethod(methodName, parameterTypes);
			Object result = method.invoke(service, arguments);
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(result);
		}
	}
}
