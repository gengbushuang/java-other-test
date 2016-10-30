package com.algorithm.basics.greedy;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 
 * @Description:TODO 霍夫曼编码(贪婪算法)
 * @author gbs
 * @Date 2016年10月30日 下午2:32:19
 */
public class Huffman {

	class Node{
		char symbol;
		int frequency;
		Node left;
		Node right;
		
		public Node(char symbol, int frequency) {
			this(symbol, frequency, null, null);
		}
		
		public Node(char symbol, int frequency,Node left,Node right){
			this.symbol=symbol;
			this.frequency = frequency;
			this.left = left;
			this.right =right;
		}
		
		public char getSymbol() {
			return symbol;
		}
		public void setSymbol(char symbol) {
			this.symbol = symbol;
		}
		public int getFrequency() {
			return frequency;
		}
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}
		@Override
		public String toString() {
			return "Node [symbol=" + symbol + ", frequency=" + frequency + "]";
		}
	}
	
	Queue<Node> priorityQueue =  new PriorityQueue<Node>(11,new Comparator<Node>() {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.getFrequency()-o2.getFrequency();
		}
		
	});
	
	public void createNode(){
		Node node = new Node('b',5);
		priorityQueue.add(node);
		node = new Node('e',10);
		priorityQueue.add(node);
		node = new Node('c',12);
		priorityQueue.add(node);
		node = new Node('a',16);
		priorityQueue.add(node);
		node = new Node('f',25);
		priorityQueue.add(node);
		node = new Node('d',17);
		priorityQueue.add(node);
	}
	
	
	public void show(){
		//创建字典，以频词排序的优先队列
		createNode();
		while(priorityQueue.size()>1){
			Node n1 = priorityQueue.poll();
			Node n2 = priorityQueue.poll();
			
			int count = n1.getFrequency()+n2.getFrequency();
			//合并成一个子树，在放到优先队列里面
			Node newNode = new Node('!',count,n1,n2);
			priorityQueue.add(newNode);
		}
		Node tmp = priorityQueue.poll();
		System.out.println(tmp);
	}
	
	public static void main(String[] args) {
		Huffman huffman = new Huffman();
		huffman.show();
	}
}
