package com.blockchain.tutorial.struct;

import java.util.Arrays;
import java.util.BitSet;

import org.apache.commons.codec.binary.Hex;

import com.blockchain.tutorial.util.BitSetConvert;
import com.blockchain.tutorial.util.Codec;

public class BitObject {
	byte[] bs;
	int n;

	public BitObject(int count) {
		this.n = count / Byte.SIZE;
		bs = new byte[n];
		Arrays.fill(bs, (byte) 0);
	}

	public void lsh(int number) {
		int count = number / Byte.SIZE;
		int lsh = number % Byte.SIZE;
		bs[n - count - 1] |= (byte) (1 << lsh);
	}

	public byte[] getBs() {
		return bs;
	}
	
	public BitSet getBitSet() {
		return BitSetConvert.byteArray2BitSet(bs);
	}

	public static void main(String[] args) {
		byte[] data1 = "I like donuts".getBytes();
		byte[] data2 = "I like donutsca07ca".getBytes();

		BitObject bitObject = new BitObject(256);
		bitObject.lsh(256 - 24);
		System.out.printf("%s\n", Hex.encodeHexString(Codec.SHA256(data1)));
		System.out.printf("%s\n", Hex.encodeHexString(bitObject.getBs()));
		System.out.printf("%s\n", Hex.encodeHexString(Codec.SHA256(data2)));
		
	}

}
