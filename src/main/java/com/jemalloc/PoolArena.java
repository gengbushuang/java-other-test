package com.jemalloc;

public abstract class PoolArena<T> {
	// 无符号右移4位
	static final int numTinySubpagePools = 512 >>> 4;
	// 最大
	private final int maxOrder;
	// 页大小
	final int pageSize;
	// 页大小左移多少位
	final int pageShifts;

	final int chunkSize;

	final int subpageOverflowMask;

	final int numSmallSubpagePools;

	private final PoolSubpage<T>[] tinySubpagePools;
	private final PoolSubpage<T>[] smallSubpagePools;

	private final PoolChunkList<T> q050;
	private final PoolChunkList<T> q025;
	private final PoolChunkList<T> q000;
	private final PoolChunkList<T> qInit;
	private final PoolChunkList<T> q075;
	private final PoolChunkList<T> q100;

	protected PoolArena(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
		this.pageSize = pageSize;
		this.maxOrder = maxOrder;
		this.pageShifts = pageShifts;
		this.chunkSize = chunkSize;

		subpageOverflowMask = ~(pageSize - 1);
		// 创建PoolSubpage数组
		tinySubpagePools = newSubpagePoolArray(numTinySubpagePools);
		for (int i = 0; i < tinySubpagePools.length; i++) {
			tinySubpagePools[i] = newSubpagePoolHead(pageSize);
		}

		numSmallSubpagePools = pageShifts - 9;
		smallSubpagePools = newSubpagePoolArray(numSmallSubpagePools);
		for (int i = 0; i < smallSubpagePools.length; i++) {
			smallSubpagePools[i] = newSubpagePoolHead(pageSize);
		}

		q100 = new PoolChunkList<T>(this, null, 100, Integer.MAX_VALUE);
		q075 = new PoolChunkList<T>(this, q100, 75, 100);
		q050 = new PoolChunkList<T>(this, q075, 50, 100);
		q025 = new PoolChunkList<T>(this, q050, 25, 75);
		q000 = new PoolChunkList<T>(this, q025, 1, 50);
		qInit = new PoolChunkList<T>(this, q000, Integer.MIN_VALUE, 25);

		q100.prevList = q075;
		q075.prevList = q050;
		q050.prevList = q025;
		q025.prevList = q000;
		q000.prevList = null;
		qInit.prevList = qInit;
	}

	private PoolSubpage<T> newSubpagePoolHead(int pageSize) {
		PoolSubpage<T> head = new PoolSubpage<T>(pageSize);
		// 设置前
		head.prev = head;
		// 设置后
		head.next = head;
		return head;
	}

	@SuppressWarnings("unchecked")
	private PoolSubpage<T>[] newSubpagePoolArray(int size) {
		return new PoolSubpage[size];
	}
}
