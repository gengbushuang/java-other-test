package com.redis.solr;

import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.redis.ReidsDb;

public class RedisSolr {

	public String[] tokenize(String content) {
		Set<String> sets = new TreeSet<String>();
		String[] words = content.split(",");
		for (String word : words) {
			sets.add(word);
		}
		return sets.toArray(new String[0]);
	}

	public void index_document(int docid, String content) {
		String[] words = tokenize(content);
		Jedis jedis = ReidsDb.DB().getJedis();
		Pipeline pipelined = jedis.pipelined();

		for (String word : words) {
			pipelined.sadd("idx:" + word, String.valueOf(docid));
		}
		pipelined.exec();
	}
}
