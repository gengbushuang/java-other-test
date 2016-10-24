package com.rpc.tcp_ip;

public class BruteForceCoding {

	private static byte byteVal = 101;//
	private static short shortVal = 10001;//
	private static int intVal = 100000001;
	private static long longVal = 1000000000001L;//

	private final static int BSIZE = Byte.SIZE;
	private final static int SSIZE = Short.SIZE;
	private final static int ISIZE = Integer.SIZE;
	private final static int LSIZE = Long.SIZE;

	private final static int BYTEMASK = 0xFF;

	public static String byteArrayToDecimalString(byte[] bArray) {
		StringBuilder rtn = new StringBuilder();
		for (byte b : bArray) {
			rtn.append(b & BYTEMASK).append(" ");
		}
		return rtn.toString();
	}

	public static int encodeIntBIgEndian(byte[] dst, long val, int offset, int size) {
		for (int i = 0; i < size; i++) {
			dst[offset++] = (byte) (val >> ((size - i - 1) * Byte.SIZE));
		}
		return offset;
	}

	public static long decodeIntBigEndian(byte[] val, int offset, int size) {
		long rtn = 0;
		for (int i = 0; i < size; i++) {
			rtn = (rtn << Byte.SIZE) | ((long) val[offset + i] & BYTEMASK);
		}
		return rtn;
	}

	public static void main(String[] args) {
//		byte[] message = new byte[BSIZE + SSIZE + ISIZE + LSIZE];
//		int offset = encodeIntBIgEndian(message, byteVal, 0, BSIZE);
//		offset = encodeIntBIgEndian(message, shortVal, offset, SSIZE);
//		offset = encodeIntBIgEndian(message, intVal, offset, ISIZE);
//		encodeIntBIgEndian(message, longVal, offset, LSIZE);
//		System.out.println("Encoded message: "+byteArrayToDecimalString(message));
		int bit5 = (1<<5);//32位
		System.out.println("bit5-->"+bit5);
		int bit7=0x80;//十进制128
		System.out.println("bit7-->"+bit7);
		int bits2and3=12;
		int bitmap = 1234567;
		//例如：1000｜100=1100，1100｜100=1100，二进制里面两个位操作是0｜1就变为1
		bitmap|=bit5;
		System.out.println("bitmap="+bitmap);
		System.out.println(bitmap &=~bit7);
		
	}
}
