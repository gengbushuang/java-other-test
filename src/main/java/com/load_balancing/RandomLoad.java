package com.load_balancing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 
 * @Description:TODO 随机负载
 * @author gbs
 * @Date 2016年10月23日 上午11:20:51
 */
public class RandomLoad extends AbsLoadBalancing{

	/**
	 * 
	 * @Description: TODO 普通随机负载
	 * @author gbs
	 * @return
	 */
	public String testRandom(){
		Map<String,Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(this.getServerWeightMap());
		
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		
		keyList.addAll(keySet);
		
		Random random = new Random();
		int index = random.nextInt(keySet.size());
		
		String server = keyList.get(index);
		return server;
	}
	
	/**
	 *
	 * @Description: TODO 加权随机负载
	 * @author gbs
	 * @return
	 */
	public String testWeightRandom(){
		Map<String,Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(this.getServerWeightMap());
		
		Set<String> keySet = serverMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		ArrayList<String> keyList = new ArrayList<String>();
		
		//权重大的数据占的比较多
		while(iterator.hasNext()){
			String server = iterator.next();
			Integer weight = serverMap.get(server);
			for(int i = 0;i<weight.intValue();i++){
				keyList.add(server);
			}
		}
		
		Random random = new Random();
		int index = random.nextInt(keySet.size());
		
		String server = keyList.get(index);
		return server;
	}
}
