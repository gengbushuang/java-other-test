package com.load_balancing;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.zip.CRC32;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description:TODO 分片
 * @author gbs
 * @Date 2016年10月23日 下午9:07:46
 */
public class SliceHash {
	
	CRC32 crc32 = new CRC32();
	
	public void shard_key(String key,int total_elements) throws UnsupportedEncodingException{
		long shard_id = 0;
		if (StringUtils.isNumeric(key)) {
			shard_id = toBinary(key);
		} else {
			long shards = 2 * total_elements;
			crc32.update(key.getBytes("ISO-8859-1"));
			shard_id = crc32.getValue() % shards;
		}
		System.out.println(shard_id);
	}
	
	private int toBinary(String key){
		int decimalInt = Integer.parseInt(key, 10);
		String binaryString = Integer.toBinaryString(decimalInt);
		int binaryInt = Integer.parseInt(binaryString, 10);
		return binaryInt;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String key = "fdfsffdf";
//		SliceHash sliceHash = new SliceHash();
//		for(int i = 0;i<1000;i++){
//		sliceHash.shard_key(String.valueOf(8),100);
//		}
		
		UUID randomUUID = UUID.randomUUID();
		String str = randomUUID.toString();
		System.out.println(str);
		String replaceAll = str.replaceAll("-", "");
		System.out.println(replaceAll);
		String substring = replaceAll.substring(0, 15);
		System.out.println(substring+":"+substring.length());
		
		char[] chars = substring.toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = substring.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		
		System.out.println(sb.toString().trim());
		
	}
}
