package com.algorithm.newsolution.ch4;
//单向链表
public class Queue<K> {

	private class Node<K> {
		private K k;
		private Node<K> next;

		public Node(K k) {
			this.k = k;
			this.next = null;
		}

		public K getK() {
			return k;
		}

		public void setK(K k) {
			this.k = k;
		}

		public Node<K> getNext() {
			return next;
		}

		public void setNext(Node<K> next) {
			this.next = next;
		}

		@Override
		public String toString() {
			return "Node [k=" + k + "]";
		}
	}

	private Node<K> head = null;
	private Node<K> tail = null;

	//将新元素加入队列
	public void push(K k) {
		Node<K> newNode = createNode(k);
		if (tail == null) {
			tail = newNode;
			tail.setNext(null);
			head = tail;
			return;
		}
		tail.setNext(newNode);
		tail = newNode;
	}

	private Node<K> createNode(K k) {
		return new Node<K>(k);
	}

	//从队列删除个元素
	public K pop() {
		if (head == null) {
			return null;
		}
		K k = head.getK();
		head = head.getNext();
		if (head == null) {
			tail = head;
		}
		return k;
	}
	//获取要移除队列的那个元素
	public K head() {
		if (head != null) {
			return head.getK();
		}
		return null;
	}

	public static void main(String[] args) {

		Queue<String> queue = new Queue<String>();
		queue.push("1");
		queue.push("12");
		queue.push("4");

		System.out.println(queue.pop());
		System.out.println(queue.pop());
		System.out.println(queue.pop());
		System.out.println(queue.pop());

		queue.push("3");

		for (int i = 0; i < 10; i++) {

		}
	}
}
