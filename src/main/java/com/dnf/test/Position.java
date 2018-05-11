package com.dnf.test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Position {
	
	static final String AD_POSITION = "test:ad:position:";
	
	List<Integer> collect;
	public Position() {
		collect = Stream.iterate(1,x->x+1)
		.limit(5000).collect(Collectors.toList());
	}
	
	Random random = new Random();

	public String getValue() {
		int nextInt = random.nextInt(collect.size());
		return AD_POSITION+collect.get(nextInt);
	}
	
	public String getV() {
		int nextInt = random.nextInt(collect.size());
		return String.valueOf(collect.get(nextInt));
	}
	
}
