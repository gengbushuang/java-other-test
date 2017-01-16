package com.load_balancing;

import java.io.UnsupportedEncodingException;
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
	
	/**
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param key
	 * @param total_elements
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public long shard_key(String key,int total_elements) throws UnsupportedEncodingException{
		long shard_id = 0;
		if (StringUtils.isNumeric(key)) {
			shard_id = toBinary(key);
		} else {
			long shards = 2 * total_elements;
			crc32.update(key.getBytes("ISO-8859-1"));
			shard_id = crc32.getValue() % shards;
		}
		return shard_id;
	}
	
	private int toBinary(String key){
		int decimalInt = Integer.parseInt(key, 10);
		String binaryString = Integer.toBinaryString(decimalInt);
		int binaryInt = Integer.parseInt(binaryString, 10);
		return binaryInt;
	}
}
