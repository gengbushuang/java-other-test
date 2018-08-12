package com.jemalloc;


public class PoolSubpage<T> {

	private final int pageSize;

	// 前
	PoolSubpage<T> prev;
	// 后
	PoolSubpage<T> next;

	PoolSubpage(int pageSize) {

		this.pageSize = pageSize;
	}
}
