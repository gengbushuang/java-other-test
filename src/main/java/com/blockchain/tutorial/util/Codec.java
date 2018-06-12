package com.blockchain.tutorial.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Codec {

	public static byte[] SHA256(byte[] input) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			return messageDigest.digest(input);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public static String getSHA256(String value) {
		String encdeStr = "";
		try {
			byte[] hash = SHA256(value.getBytes("UTF-8"));
			encdeStr = Hex.encodeHexString(hash);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}

	// 前64个素数的立方根小数
	private static int[] K = { 0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4,
			0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
			0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152,
			0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138,
			0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70,
			0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
			0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa,
			0xa4506ceb, 0xbef9a3f7, 0xc67178f2 };
	// 前8个素数的平方根小数
	private static int[] H = { 0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab,
			0x5be0cd19 };

	private static byte[] tmp_b = { 0, 1 };

	public static void MySHA256(String value) throws UnsupportedEncodingException {
		byte[] bytes = value.getBytes("utf-8");
		int length = bytes.length * Byte.SIZE;
		ByteBuffer buffer = ByteBuffer.allocate(64);
		buffer.put(bytes);
		for (int i = 0; (buffer.position() * Byte.SIZE) % 512 != 448; i++) {
			if (i == 0) {
				buffer.put(tmp_b[1]);
			} else {
				buffer.put(tmp_b[0]);
			}
		}
		System.out.println(buffer.position());
		if (length < 512) {
			buffer.putInt(0);
			buffer.putInt(length);
		}

		System.out.println(buffer.position());

		// int length = bytes.length * Byte.SIZE;
		// StringBuilder sb = new StringBuilder(512 > length ? 512 - length : 10);
		// while ((length + sb.length()) % 512 != 448) {
		// if (sb.length() > 0) {
		// sb.append("0");
		// } else {
		// sb.append("1");
		// }
		// }
		// if (length < 512) {
		// System.out.println("原始消息长度:" + length);
		// String binaryString = Long.toBinaryString(length);
		// for (int i = 0; i < 64 - binaryString.length(); i++) {
		// sb.append("0");
		// }
		// sb.append(binaryString);
		// }
		// System.out.println(sb.toString());
		// System.out.println(Arrays.toString(bytes));
		// System.out.println("总长度:" + (length + sb.length()));
	}

	// 97 0110 0001
	// 98 0110 0010
	// 99 0110 0011
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String value = "xvxvxdsfsawqADADLLAOFOAFsdfsdfsdfsdfsdfdfdfsdfsdfsdfsdfsdfsdfsdfsdfdsf";
//		value = "xvxvxdsdsd";
//		Codec.SHA256(value);
//		String sha256 = Codec.getSHA256(value);
//		System.out.println(sha256);
//
//		String va = "abc";
//		Codec.SHA256(va);

	}
}
