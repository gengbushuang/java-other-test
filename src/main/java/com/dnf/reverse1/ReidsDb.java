package com.dnf.reverse1;

import java.util.function.Function;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ReidsDb {

	private static ReidsDb db = new ReidsDb();

	JedisPool pool;
	
	private ReidsDb() {
		pool = new JedisPool("10.10.40.28",9990);
	}

	public static ReidsDb DB() {
		return db;
	}
	
	@Deprecated
	public void returnResource(Jedis jedis) {
		if(jedis!=null) {
			pool.returnResource(jedis);
		}
	}
	
	@Deprecated
	public void returnBrokenResource(Jedis jedis) {
		if(jedis!=null) {
			pool.returnBrokenResource(jedis);
		}
	}
	
	public Jedis getJedis(){
		return pool.getResource();
	}
	
	
	public <R> R callback(Function<Jedis, R> function){
		try(Jedis jedis = pool.getResource();){
			return function.apply(jedis);
		}
	}
	
	public static void main(String[] args) {
		Long callback = ReidsDb.DB().callback(x->{
			return x.dbSize();
		});
		System.out.println(callback);
	}
}
