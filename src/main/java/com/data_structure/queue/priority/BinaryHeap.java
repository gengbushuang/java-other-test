package com.data_structure.queue.priority;

import java.util.Random;

/**
 * 优先队列堆实现
 * @author gbs
 *
 */
public class BinaryHeap {

	private int[] i;

	private int count = 0;

	public BinaryHeap() {

	}

	public BinaryHeap(int count) {
		this.i = new int[count];
	}

	public void insert(int n) {
		if (count == i.length - 1) {
			System.out.println("!");
			return;
		}
		int h = ++count;
		for (; h > 1 & n < i[h / 2]; h /= 2) {
			i[h] = i[h / 2];
		}
		i[h] = n;
	}

	public int remove() {
		if (count == 0) {
			return -1;
		}
		int h = 1;
		int resutl = i[h];
		i[h]=i[count--];
		int tmp = i[h];
		int child;
		for (;h*2<=count;h=child) {
			child=h*2;
			if(child != count && i[child+1]<i[child]){
				child++;
			}
			if(i[child]<tmp){
				i[h]=i[child];
			}else{
				break;
			}
			
		}
		i[h] = tmp;
		return resutl;
	}

	public static void main(String[] args) {
		BinaryHeap heap = new BinaryHeap(30);
		Random r = new Random();
		for (int i = 0; i < 20; i++) {
			int nextInt = r.nextInt(100);
			System.out.print(nextInt+" ");
			heap.insert(nextInt);
		}
		System.out.println();
		System.out.println();
		for (int i = 0; i < 20; i++) {
			int remove = heap.remove();
			System.out.print(remove+" ");
		}
	}

}
