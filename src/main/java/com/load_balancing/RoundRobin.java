package com.load_balancing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @Description:TODO 轮询负载
 * @author gbs
 * @Date 2016年10月23日 上午10:13:57
 */
public class RoundRobin extends AbsLoadBalancing {
	
	private AtomicInteger atomicInteger = new AtomicInteger();
	
	/**
	 * 
	 * @Description: TODO 普通轮询
	 * @author gbs
	 * @return
	 */
	public String testRoundRobin(){
		Map<String,Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(this.getServerWeightMap());
		
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		
		keyList.addAll(keySet);
		//轮询获取索引
		int newIndex = newIndex(keyList);
		return keyList.get(newIndex);
	}
	
	/**
	 * 
	 * @Description: TODO 加权轮询
	 * @author gbs
	 * @return
	 */
	public String testWeightRoundRobin(){
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
		
		int newIndex = newIndex(keyList);
		return keyList.get(newIndex);
	}
	
	private final int newIndex(ArrayList<String> keyList){
		for(;;){
			int expect = atomicInteger.get();
			int update = 0;
			if(expect<keyList.size()){
				update = expect+1;
			}
			if(atomicInteger.compareAndSet(expect, update)){
				return update;
			}
		}
	}
}
