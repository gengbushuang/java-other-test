package com.redis.solr;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.redis.ReidsDb;

public class ReadSearch {

	public void set_common(Set<String>names){
		Set<String> collect = names.stream().map(x->"idx:"+x).collect(Collectors.toSet());
		Jedis jedis = ReidsDb.DB().getJedis();
		Pipeline pipelined = jedis.pipelined();
		//pipelined.expire(key, seconds);
		//交集
		//pipelined.sinterstore(dstkey, keys)
		
		//并集
		//pipelined.sunionstore(dstkey, keys)
		
		//差集
		//pipelined.sdiffstore(dstkey, keys)
	}
}
