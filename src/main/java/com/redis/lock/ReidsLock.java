package com.redis.lock;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class ReidsLock {

	public String acquire_lock(Jedis jedis, String lockname, long acquire_timeout) {
		String identifier = UUID.randomUUID().toString();
		long end = System.currentTimeMillis() + acquire_timeout;
		while (System.currentTimeMillis() < end) {
			Long setnx = jedis.setnx("lock:" + lockname, identifier);// 尝试获取锁
			if (setnx == 1) {
				return identifier;
			}
			try {
				TimeUnit.MICROSECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public boolean release_lock(Jedis jedis, String lockname, String identifier) {
		String key_lockname = "lock:" + lockname;

		while (true) {
			jedis.watch(key_lockname);
			String string = jedis.get(key_lockname);
			if (!string.equals(identifier)) {// 检查进程是否仍然持有锁
				jedis.unwatch();
				break;
			}
			// 释放锁
			Transaction multi = jedis.multi();
			multi.del(key_lockname);
			List<Object> exec = multi.exec();
			if (exec == null) {
				jedis.unwatch();
				break;
			}
			return true;
		}
		return false;
	}

	public String acquire_lock_with_timeout(Jedis jedis, String lockname, long acquire_timeout, int lock_time) {
		String identifier = UUID.randomUUID().toString();
		long end = System.currentTimeMillis() + acquire_timeout;
		String key_lockname = "lock:" + lockname;
		while (System.currentTimeMillis() < end) {
			// 获取锁并设置过期时间
			Long setnx = jedis.setnx(key_lockname, identifier);
			if (setnx == 1) {
				jedis.expire(key_lockname, lock_time);
				return identifier;
			}
			// 检查过期时间，并在有需要时对其进行更新
			Long ttl = jedis.ttl(key_lockname);
			if (ttl == -1) {
				jedis.expire(key_lockname, lock_time);
				return identifier;
			}
			try {
				TimeUnit.MICROSECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
}
