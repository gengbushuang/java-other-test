package com.redis.ad;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.redis.ReidsDb;

public class Assignment {

	public static void main(String[] argss) throws IOException {
		String path = Assignment.class.getResource("test.lua").getPath();
		//System.out.println(path);
		String script = Files.toString(new File(path), Charset.forName("utf-8"));
		
//		Object callback = ReidsDb.DB().callback(readis->{
//			return readis.scriptLoad(script);
//		});
//		System.out.println(callback);
		
		//System.out.println(script);
		
//		String key1 = "SMEMBERS";
//		String key = "midas:unit:index:model:7:高端";
		ArrayList<String> keys = Lists.newArrayList("midas:index:sex:MAN:", "midas:index:model:中端:", "midas:index:carrier:SKT:", "midas:index:la:Latin:","midas:index:ver:android_3:", "midas:index:country:AD:", "midas:index:age:JUVENILE:", "midas:index:netswork:移动网络:", "midas:index:group:MOSLEMYES:");
//		ArrayList<String> keys = Lists.newArrayList("midas:index:group:MOSLEMNO:", "midas:index:sex:WOMAN:","midas:index:la:Kuwait:");
		ArrayList<String> args = Lists.newArrayList("9","midas:index:zall:0","_");
		Object callback = ReidsDb.DB().callback(readis->{
			return readis.evalsha("fd3e0df1d1c69c325668f65d03869ff133b5cfc2",keys,args);
//			return readis.eval("return redis.call(KEYS[1],KEYS[2])",2,key1,key);
//			return readis.eval("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", keys, args);
			//readis.eval("return redis.call('set',KEYS[1],ARGV[1])", keyCount, params);
		});
		System.out.println(callback.getClass());
		System.out.println(callback);
		if(callback instanceof ArrayList){
			ArrayList list = (ArrayList) callback;
			for(Object o :list){
				System.out.println(o.getClass());
				if(o instanceof ArrayList){
					ArrayList olist = (ArrayList) o;
					for(Object o1:olist){
						System.out.println(o1);
					}
				}
//				
			}
		}
	}
}
