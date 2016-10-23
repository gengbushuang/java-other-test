package com.load_balancing;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsLoadBalancing {

	private volatile Map<String,Integer> serverWeightMap = new HashMap<String,Integer>();

	public void setServerWeightMap(Map<String, Integer> serverWeightMap) {
		this.serverWeightMap = serverWeightMap;
	}

	public Map<String, Integer> getServerWeightMap() {
		return serverWeightMap;
	}
	
}
