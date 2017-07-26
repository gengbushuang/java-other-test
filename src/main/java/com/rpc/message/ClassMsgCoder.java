package com.rpc.message;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;

public class ClassMsgCoder implements MsgCoder {
	private String methodName;
	private Class<?>[] parameterClasses;
	private Object[] parameters;

	public ClassMsgCoder(Method method, Object[] parameters) {
		this.methodName = method.getName();
		this.parameterClasses = method.getParameterTypes();
		this.parameters = parameters;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(methodName);
		out.writeInt(parameterClasses.length);
		for (int i = 0; i < parameters.length; i++) {
			writeMethod(out, parameters[i], parameterClasses[i]);
		}
	}

	private void writeMethod(DataOutput out, Object parameter, Class<?> parameterType) {
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub

	}

}
