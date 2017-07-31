package com.rpc;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import com.rpc.message.ClassMsgCoder;

public class Provider {

	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ServerSocket server = new ServerSocket(12345);
		while(true){
			Socket socket = server.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			ClassMsgCoder classMsgCoder = new ClassMsgCoder();
			classMsgCoder.readFields(dis);
			System.out.println(classMsgCoder);
			//////////////////////////////////
			Class serviceinterfaceclass = Class.forName(classMsgCoder.getDeclaringClassProtocolName());
			Object service = new SayHelloServiceImpl();
			Method method = serviceinterfaceclass.getMethod(classMsgCoder.getMethodName(), classMsgCoder.getParameterClasses());
			Object result = method.invoke(service, classMsgCoder.getParameters());
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(result);
		}
	}
}
