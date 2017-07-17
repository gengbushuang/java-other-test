package com.algorithm.newsolution.ch4;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 循环队列
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月17日 下午3:12:57
 * @param <K>
 */
public class BufferQueue<K> {

	private K[] ks;
	private int size;

	private int head;
	private int tail;

	public BufferQueue(Class<K> cla, int size) {
		// ParameterizedType parameterizedType = (ParameterizedType)
		// this.getClass().getGenericSuperclass();
		// Class clazz = (Class<K>)
		// parameterizedType.getActualTypeArguments()[0];
		// System.out.println("clazz ==>> " + clazz);
		ks = (K[]) Array.newInstance(cla, size);
		this.size = size;
		this.head = this.tail = 0;
	}

	// 判断队列是否为空
	public boolean isEmpty() {
		return head == tail;
	}

	// 添加元素
	public boolean push(K k) {
		if (fullQ()) {
			return false;
		}
		ks[tail++] = k;
		// 添加元素的的下标是否到底边界，是的话就设置为0
		tail -= (tail < size ? 0 : size);
		return true;
	}

	// 判断队列是否满了
	public boolean fullQ() {
		return (tail + 1 == size) ? head == 0 : (tail + 1) == head;
	}

	public K pop() {
		if (isEmpty()) {
			return null;
		}
		K k = ks[head++];
		// 取元素的的下标是否到底边界，是的话就设置为0
		head -= (head < size ? 0 : size);
		return k;
	}

	public void show() {
		System.out.println(Arrays.toString(ks));
	}

	public static void main(String[] args) {
		BufferQueue<String> bufferQueue = new BufferQueue<String>(String.class, 10);
		for (int i = 0; i < 15; i++) {
			if (!bufferQueue.push("a" + i)) {
				bufferQueue.pop();
				bufferQueue.push("a" + i);
			}
			bufferQueue.show();
		}
	}
}
