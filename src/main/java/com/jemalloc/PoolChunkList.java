package com.jemalloc;

public final class PoolChunkList<T> {
	// 当前父指向
	private final PoolArena<T> arena;
	// 下一个
	private final PoolChunkList<T> nextList;
	// 上一个
	PoolChunkList<T> prevList;

	private final int minUsage;
	private final int maxUsage;

	PoolChunkList(PoolArena<T> arena, PoolChunkList<T> nextList, int minUsage, int maxUsage) {
		this.arena = arena;
		this.nextList = nextList;
		this.minUsage = minUsage;
		this.maxUsage = maxUsage;
	}
}
