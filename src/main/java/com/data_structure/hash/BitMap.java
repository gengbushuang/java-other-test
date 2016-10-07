package com.data_structure.hash;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class BitMap {

	private final static int BITSPERWORD = 64;
	private final static int SHIFT = 6;
	private final static byte MASK = 0x3F;
	private final static int N = 1<<24;

	private long a[] = new long[1 + N / BITSPERWORD];

	public BitMap() {

	}

	public void add(String value,int hashCount){
		int[] hashResult = getHashResult(value.getBytes(), hashCount, N);
		for(int hashVal :hashResult){
			set(hashVal);
		}
	}

	public boolean isPresent(String value,int hashCount){
		int[] hashResult = getHashResult(value.getBytes(), hashCount, N);
		for(int hashVal :hashResult){
			if(test(hashVal)==0){
				return false;
			}
		}
		return true;
	}
	
	public void set(int i) {
//		System.out.println("i >> SHIFT=="+(i >> SHIFT));
//		System.out.println("1L << i=="+(1L << i));
//		a[i >> SHIFT] |= (1L << (i & MASK));
		a[i >> SHIFT] |= (1L << (i & MASK));
//		System.out.println(a[i >> SHIFT]);
	}

	public long test(int i) {
		return a[i >> SHIFT] & (1L << (i & MASK));
	}
	
	public int[] getHashResult(byte[] b, int hashCount, int size) {
		int[] result = new int[hashCount];
		int hash1 = MurmurHash.hash(b, b.length, 0);
		int hash2 = MurmurHash.hash(b, b.length, hash1);
		for (int i = 0; i < hashCount; i++) {
			result[i] = Math.abs((hash1 + i * hash2) % size);
		}
		return result;
	}

	public static void main(String[] args) {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		System.out.println(memoryMXBean.getHeapMemoryUsage());
		BitMap bitMap = new BitMap();
		for(int i = 1;i<35;i++){
			bitMap.set(i);
		}
		System.out.println(memoryMXBean.getHeapMemoryUsage());
	}

}
