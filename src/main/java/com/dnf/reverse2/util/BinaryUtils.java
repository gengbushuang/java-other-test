package com.dnf.reverse2.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Term;

public class BinaryUtils {

	public final static int ASSIGN_HEAD_SIZE = Integer.BYTES * 2 + 1;

	public final static int CONJ_HEAD_SIZE = Integer.BYTES * 3;

	public final static int TERM_HEAD_SIZE = Integer.BYTES * 3;

	// long转换byte
	public static byte[] longToBytes(long value) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(value);
		return buffer.array();
	}

	// byte转换long
	public static long bytesToLong(byte[] bytes, int offset) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, Long.BYTES);
		return buffer.getLong();
	}

	// int转换byte
	public static byte[] intToBytes(int value) {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(value);
		return buffer.array();
	}

	// byte转换int
	public static int bytesToInt(byte[] bytes, int offset) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, Integer.BYTES);
		return buffer.getInt();
	}

	// boolean转换byte
	public static byte booleanToByte(boolean value) {
		return value ? (byte) 1 : (byte) 0;
	}

	// byte转换boolean
	public static boolean byteToBoolean(byte value) {
		return value != 0;
	}

	public static byte[] assignToByte(Assignment assignment) {
		List<Integer> terms = assignment.getTerms();
		List<byte[]> bytes = new ArrayList<>(terms.size());
		int size = 0;
		for (int termId : terms) {
			byte[] intToBytes = intToBytes(termId);
			size += intToBytes.length;
			bytes.add(intToBytes);
		}
		ByteBuffer buffer = ByteBuffer.allocate(ASSIGN_HEAD_SIZE + size);

		buffer.put(intToBytes(assignment.getId()));
		buffer.put(booleanToByte(assignment.isBelong()));
		buffer.put(intToBytes(size));
		if (!bytes.isEmpty()) {
			for (byte[] bs : bytes) {
				buffer.put(bs);
			}
		}
		return buffer.array();
	}

	public static byte[] conjToByte(Conjunction conjunction) {
		List<Integer> assigns = conjunction.getAssigns();
		List<byte[]> bytes = new ArrayList<>(assigns.size());
		int size = 0;
		for (int assignId : assigns) {
			byte[] intToBytes = intToBytes(assignId);
			size += intToBytes.length;
			bytes.add(intToBytes);
		}

		ByteBuffer buffer = ByteBuffer.allocate(CONJ_HEAD_SIZE + size);

		buffer.put(intToBytes(conjunction.getId()));
		buffer.put(intToBytes(conjunction.getSize()));
		buffer.put(intToBytes(size));
		if (!bytes.isEmpty()) {
			for (byte[] bs : bytes) {
				buffer.put(bs);
			}
		}
		return buffer.array();
	}

	public static byte[] termToByte(Term term) {
		String key = term.getKey();
		byte[] bytes_key = key.getBytes();

		String value = term.getValue();
		byte[] bytes_value = value.getBytes();

		ByteBuffer buffer = ByteBuffer.allocate(TERM_HEAD_SIZE + bytes_key.length + bytes_value.length);

		buffer.put(intToBytes(term.getId()));
		buffer.putInt(bytes_key.length);
		buffer.put(bytes_key);

		buffer.putInt(bytes_value.length);
		buffer.put(bytes_value);

		return buffer.array();
	}

	public static Term byteToTerm(byte[] data) {
		ByteBuffer wrap = ByteBuffer.wrap(data);

		int id = wrap.getInt();

		int key_length = wrap.getInt();
		byte[] bytes = new byte[key_length];
		wrap.get(bytes);
		String key = new String(bytes);

		int value_length = wrap.getInt();
		bytes = new byte[value_length];
		wrap.get(bytes);
		String value = new String(bytes);

		return new Term(id, key, value);
	}

	public static Conjunction byteToConj(byte[] data) {
		ByteBuffer wrap = ByteBuffer.wrap(data);
		int id = wrap.getInt();

		int size = wrap.getInt();

		int length = wrap.getInt();

		byte[] bytes = new byte[length];
		wrap.get(bytes);

		List<Integer> integers = byteToInteger(bytes);

		return new Conjunction(integers, size, id);
	}

	public static Assignment byteToAssign(byte[] data) {
		ByteBuffer wrap = ByteBuffer.wrap(data);
		int id = wrap.getInt();
		boolean belong = byteToBoolean(wrap.get());

		int length = wrap.getInt();
		byte[] bytes = new byte[length];
		wrap.get(bytes);

		List<Integer> integers = byteToInteger(bytes);

		return new Assignment(integers, belong, id);
	}

	public static List<Integer> byteToInteger(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		List<Integer> integers = new ArrayList<>();
		while (buffer.hasRemaining()) {
			Integer integer = buffer.getInt();
			integers.add(integer);
		}
		return integers;
	}

}