package com.redis.ratelimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.redis.ReidsDb;

public class RedisRateLimiter {

	/**
	 * 时间窗口进行限流，不太适合大流量
	 * 
	 * @param user_id
	 * @param action_key
	 * @param period
	 * @param max_count
	 * @return
	 */
	public boolean is_action_allowed(String user_id, String action_key, int period, int max_count) {
		String key = String.format("hist:%s:%s'", user_id, action_key);
		long time = System.currentTimeMillis();
		List<Object> syncAndReturnAll;
		try (Jedis jedis = ReidsDb.DB().getJedis();) {
			Pipeline pipelined = jedis.pipelined();
			// 记录用户的动作行为
			pipelined.zadd(key, time, String.valueOf(time));
			// 移除以前时间窗口
			pipelined.zremrangeByScore(key, 0, time - TimeUnit.SECONDS.toMillis(period));
			// 获取窗口内数目
			pipelined.zcard(key);
			// 设置过期时间
			pipelined.expire(key, period + 1);
			syncAndReturnAll = pipelined.syncAndReturnAll();
		}

		Object object = syncAndReturnAll.get(2);

		return Integer.parseInt(object.toString()) <= max_count;
	}

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		long convert = TimeUnit.SECONDS.convert(60, TimeUnit.SECONDS);
		System.out.println(convert);
		System.out.println(TimeUnit.SECONDS.toMillis(60));
	}
}
