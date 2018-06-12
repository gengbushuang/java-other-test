package com.blockchain.tutorial.util;

import java.util.BitSet;

public class BitSetConvert {

	public static BitSet byteArray2BitSet(byte[] bytes) {
		BitSet bitSet = new BitSet(bytes.length * 8);
		int index = 0;
		for (byte b : bytes) {
			for (int j = 7; j >= 0; j--) {
				bitSet.set(index++, (b & (1 << j)) >> j == 1 ? true : false);
			}
		}
		return bitSet;
	}

	public static byte[] bitSet2ByteArray(BitSet bitSet) {
		byte[] bytes = new byte[bitSet.size() / 8];
		for (int i = 0; i < bitSet.size(); i++) {
			int index = i / 8;
			int offset = 7 - i % 8;
			bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
		}
		return bytes;
	}

	public static int cmp(byte[] b1, byte[] b2) {
		short tmp;
		for (int i = 0; i < b1.length; i++) {
			tmp = (short) (b1[i] < 0 ? (~b1[i] + 1) : b1[i]);
			if (tmp - b2[i] < 0) {
				return -1;
			} else if (tmp - b2[i] > 0) {
				return 1;
			}
		}
		return 0;
	}
}
