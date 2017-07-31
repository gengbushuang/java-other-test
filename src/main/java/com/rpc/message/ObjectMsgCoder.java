package com.rpc.message;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class ObjectMsgCoder implements MsgCoder {
	private Class parameterClasses;
	private Object parameters;

	public ObjectMsgCoder() {

	}

	public ObjectMsgCoder(Class declaredClass, Object instance) {
		this.parameterClasses = declaredClass;
		this.parameters = instance;
	}

	public Class getParameterClasses() {
		return parameterClasses;
	}

	public Object getParameters() {
		return parameters;
	}
	
	private static final Map<String, Class<?>> PRIMITIVE_NAMES = new HashMap<String, Class<?>>();
	static {
		PRIMITIVE_NAMES.put("boolean", Boolean.TYPE);
		PRIMITIVE_NAMES.put("byte", Byte.TYPE);
		PRIMITIVE_NAMES.put("char", Character.TYPE);
		PRIMITIVE_NAMES.put("short", Short.TYPE);
		PRIMITIVE_NAMES.put("int", Integer.TYPE);
		PRIMITIVE_NAMES.put("long", Long.TYPE);
		PRIMITIVE_NAMES.put("float", Float.TYPE);
		PRIMITIVE_NAMES.put("double", Double.TYPE);
		PRIMITIVE_NAMES.put("void", Void.TYPE);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		writeObject(out, parameters, parameterClasses);
	}

	public static void writeObject(DataOutput out, Object instance, Class declaredClass) throws IOException {
		if (instance == null) {

		}
		out.writeUTF(declaredClass.getName());

		if (declaredClass.isArray()) {// 判断是否数组
			int length = Array.getLength(instance);
			out.writeInt(length);
			for (int i = 0; i < length; i++) {
				writeObject(out, Array.get(instance, i), declaredClass.getComponentType());
			}
		} else if (declaredClass == String.class) {// 判断是否字符串
			out.writeUTF((String) instance);
		} else if (declaredClass.isPrimitive()) {// 判断是否为原始类型（boolean、char、byte、short、int、long、float、double）
			if (declaredClass == Boolean.TYPE) { // boolean
				out.writeBoolean(((Boolean) instance).booleanValue());
			} else if (declaredClass == Character.TYPE) { // char
				out.writeChar(((Character) instance).charValue());
			} else if (declaredClass == Byte.TYPE) { // byte
				out.writeByte(((Byte) instance).byteValue());
			} else if (declaredClass == Short.TYPE) { // short
				out.writeShort(((Short) instance).shortValue());
			} else if (declaredClass == Integer.TYPE) { // int
				out.writeInt(((Integer) instance).intValue());
			} else if (declaredClass == Long.TYPE) { // long
				out.writeLong(((Long) instance).longValue());
			} else if (declaredClass == Float.TYPE) { // float
				out.writeFloat(((Float) instance).floatValue());
			} else if (declaredClass == Double.TYPE) { // double
				out.writeDouble(((Double) instance).doubleValue());
			} else if (declaredClass == Void.TYPE) { // void
			} else {
				throw new IllegalArgumentException("Not a primitive: " + declaredClass);
			}
		} else if (declaredClass.isEnum()) {// 判断是否为枚举
			out.writeUTF(((Enum) instance).name());
		} else if (MsgCoder.class.isAssignableFrom(declaredClass)) {// 判断是否是这个超类
			out.writeUTF(instance.getClass().getName());
			((MsgCoder) instance).write(out);
		} else {
			throw new IOException("找不到这个类 " + instance + " as " + declaredClass);
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		readObject(in, this);
	}

	public static Object readObject(DataInput in) throws IOException {
		return readObject(in, null);
	}

	public static Object readObject(DataInput in, ObjectMsgCoder objectMsgCoder) throws IOException {
		String className = in.readUTF();
		Class<?> declaredClass = PRIMITIVE_NAMES.get(className);
		if (declaredClass == null) {
			declaredClass = loadClass(className);
		}
		Object instance = null;

		if (declaredClass.isPrimitive()) {
			if (declaredClass == Boolean.TYPE) { // boolean
				instance = Boolean.valueOf(in.readBoolean());
			} else if (declaredClass == Character.TYPE) { // char
				instance = Character.valueOf(in.readChar());
			} else if (declaredClass == Byte.TYPE) { // byte
				instance = Byte.valueOf(in.readByte());
			} else if (declaredClass == Short.TYPE) { // short
				instance = Short.valueOf(in.readShort());
			} else if (declaredClass == Integer.TYPE) { // int
				instance = Integer.valueOf(in.readInt());
			} else if (declaredClass == Long.TYPE) { // long
				instance = Long.valueOf(in.readLong());
			} else if (declaredClass == Float.TYPE) { // float
				instance = Float.valueOf(in.readFloat());
			} else if (declaredClass == Double.TYPE) { // double
				instance = Double.valueOf(in.readDouble());
			} else if (declaredClass == Void.TYPE) { // void
				instance = null;
			} else {
				throw new IllegalArgumentException("Not a primitive: " + declaredClass);
			}
		} else if (declaredClass.isArray()) {
			int length = in.readInt();
			instance = Array.newInstance(declaredClass.getComponentType(), length);
			for (int i = 0; i < length; i++) {
				Array.set(instance, i, readObject(in));
			}
		} else if (declaredClass == String.class) {
			instance = in.readUTF();
		} else if (declaredClass.isEnum()) {
			instance = Enum.valueOf((Class<? extends Enum>) declaredClass, in.readUTF());
		} else if (MsgCoder.class.isAssignableFrom(declaredClass)) {

		} else {
			throw new IOException("ff");
		}
		
		if (objectMsgCoder != null) {                 // store values
			objectMsgCoder.parameterClasses = declaredClass;
			objectMsgCoder.parameters = instance;
		}
		
		return instance;
	}

	private static Class<?> loadClass(String className) {
		Class<?> declaredClass = null;
		try {
			declaredClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("没有找到这个类 " + className, e);
		}
		return declaredClass;
	}
}
