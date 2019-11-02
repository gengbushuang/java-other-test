package com.load_balancing;

import sun.misc.CRC16;

public class HashSlot {

	CRC16 crc16 = new CRC16();
//	byte [] bs  = new byte[]{0x5300,0x00A1};
	public long hashToLong(){
		int  i =21409;
		String binaryString = Integer.toBinaryString(i);
		System.out.println(binaryString);
		
		byte b = 0x01;
		
		//crc16.
//		crc16.update(arg0);
		return 0;
	}
	
	public int g(int x) {
		return 1 << x;
	}
	
	public static void main(String[] args) {
		long rex = new HashSlot().hashToLong();
		System.out.println(rex);
	}
}
