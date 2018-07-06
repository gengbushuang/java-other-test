package com.totp;

import java.nio.ByteBuffer;

public class Base64String {
	private String str;

	private char[] chars;
	
	private int shift;

	public Base64String(String str) {
		this.str = str;
		this.chars = str.toCharArray();
		this.shift = Integer.numberOfTrailingZeros(chars.length);
		System.out.println(shift);
	}

	public byte[] decode(String encoded) {
		return decodeInternal(encoded);
	}

	protected byte[] decodeInternal(String encoded) {
		byte[] bytes = encoded.getBytes();
		int length = (bytes.length+2) * Byte.SIZE;
		int group = shift*4;
		int count = length/group;
		System.out.println(count);
		byte[] result = new byte[count*4];
		for(int i = 0;i<count;i++) {
			int val = bytes[i]<<16|bytes[i+1]<<8|bytes[i+2];
			result[i] = (byte) ((val>>18)&0x3f);
			result[i+1] =(byte) ((val>>12)&0x3f);
			result[i+2] =(byte) ((val>>6)&0x3f);
			result[i+3] =(byte) (val&0x3f);
		}
		
		StringBuilder sb = new StringBuilder(count*4);
		for(byte b : result) {
			sb.append(chars[b]);
		}
		System.out.println(sb.toString());
		return new byte[0];
	}

	public static void main(String[] args) {
		Base64String base64 = new Base64String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
		base64.decode("!@#");
	}
}
