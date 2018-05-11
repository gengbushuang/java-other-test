package com.dnf.test;

import java.util.List;

import redis.clients.jedis.Jedis;

public interface QueryCall {

	public List<Object> query(Query query,Jedis jedis);
}
