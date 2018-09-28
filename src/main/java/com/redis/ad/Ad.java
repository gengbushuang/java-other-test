package com.redis.ad;

import com.redis.ReidsDb;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class Ad {

	enum Ecpm {
		CPM {
			@Override
			float to_ecpm(int views, int clicks, float cpm) {
				return cpm;
			}
		},

		CPC {
			// 程序只要将广告的每次点击价格乘以广告的点击通过率(ctr),然后在乘以1000,得出的结果就是广告的eCPM.
			// 其中点击通过率可以用广告被点击的次数除以广告展示的次数计算得出.
			// 每次点击价格为0.25,通过率为0.2%,ecpm=2.15*0.002*1000=0.5;
			@Override
			float to_ecpm(int views, int clicks, float cpc) {
				return 1000 * cpc * (clicks / views);
			}
		},
		CPA {
			// 广告点击通过率0.2%,用户执行动作概率为10%,广告的cpa为3,ecpm=0.002*0.1*3*1000=0.60
			@Override
			float to_ecpm(int views, int actions, float cpa) {
				return 1000 * cpa * (actions / views);

			}
		};
		
		public static Ecpm toVlues(int type){
			switch (type) {
			case 1:
				return CPC;
			case 2:
				return CPA;
			default:
				return CPM;
			}
		}

		abstract float to_ecpm(int t1, int t2, float t3);
	}

	public void index_ad(int id, String[] locations, String content,int type,float value) {
		String str_id = String.valueOf(id);
		Jedis jedis = ReidsDb.DB().getJedis();
		Pipeline pipelined = jedis.pipelined();
		// 把id广告添加到所有相关的位置集合里面。
		for (String location : locations) {
			pipelined.sadd("idx:req:" + location, str_id);
		}
		//对广告包含单词进行索引
		String[] words = tokenize(content);
		for (String word : words) {
			pipelined.zadd("idx:"+word, 0,str_id);
		}
		
		float to_ecpm = Ecpm.toVlues(type).to_ecpm(1000, 10, value);
		//记录这个广告类型
		pipelined.hset("type:", str_id, String.valueOf(type));
		//将广告的eCPM添加到一个记录了所有广告的eCPM的有序集合里面
		pipelined.zadd("idx:ad:value:", to_ecpm, str_id);
		//将广告的基本价格添加到一个记录了所有广告的基本价格的有序机会里面
		pipelined.zadd("ad:base_value:", value, str_id);
		//能够对广告进行定向的单词全部记录起来。
		pipelined.sadd("terms:"+id, words);
		pipelined.exec();
	}

	private String[] tokenize(String content) {
		return new String[0];
	}
}
