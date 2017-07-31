package com.rpc.message;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;

public class ClassMsgCoder implements MsgCoder {
	private String methodName;
	private Class<?>[] parameterClasses;
	private Object[] parameters;
	private String declaringClassProtocolName;

	public ClassMsgCoder(){}
	
	public ClassMsgCoder(Method method, Object[] parameters) {
		this.methodName = method.getName();
		this.parameterClasses = method.getParameterTypes();
		this.parameters = parameters;

		this.declaringClassProtocolName = method.getDeclaringClass().getName();
	}

	public String getMethodName() {
		return methodName;
	}

	public Class<?>[] getParameterClasses() {
		return parameterClasses;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public String getDeclaringClassProtocolName() {
		return declaringClassProtocolName;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(declaringClassProtocolName);
		out.writeUTF(methodName);
		out.writeInt(parameterClasses.length);
		for (int i = 0; i < parameters.length; i++) {
			writeMethod(out, parameters[i], parameterClasses[i]);
		}
	}

	private void writeMethod(DataOutput out, Object parameter, Class<?> parameterType) throws IOException {
		ObjectMsgCoder.writeObject(out, parameter, parameterType);
	}

	@SuppressWarnings("static-access")
	@Override
	public void readFields(DataInput in) throws IOException {
		declaringClassProtocolName = in.readUTF();
		methodName = in.readUTF();
		parameters = new Object[in.readInt()];
		parameterClasses = new Class[parameters.length];
		ObjectMsgCoder objectMsgCoder = new ObjectMsgCoder();
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = objectMsgCoder.readObject(in, objectMsgCoder);
			parameterClasses[i] = objectMsgCoder.getParameterClasses();
		}
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(methodName);
		buffer.append("(");
		for (int i = 0; i < parameters.length; i++) {
			if (i != 0)
				buffer.append(", ");
			buffer.append(parameters[i]);
		}
		buffer.append(")");
		return buffer.toString();
	}
}
