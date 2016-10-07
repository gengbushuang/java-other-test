package com.data_structure.tree;

import java.util.Random;

public class BTree {

	private static final int M = 5;

	private BNode root;

	class BNode {
		boolean b = false;
		//
		BNode[] node = new BNode[M];
		//
		int[] key = new int[M];
		// 关键字个数
		int keyCount = 0;

		public BNode(int n) {
			keyCount = n;
		}
		public BNode(int n,boolean b) {
			this(n);
			this.b = b;
		}
		
		public void add(int index, int value) {
			key[index] = value;
		}
		
		public void swap(int i1,int i2){
			key[i1]=key[i2];
		}
		
		public void add(int index,int value,BNode n){
			add(index, value);
			node[index] = n;
		}

		public void insert(int index, int value) {
			add(index, value);
			stepSize();
		}
		
		public void insert(int index,int value,BNode n) {
			add(index, value,n);
			stepSize();
		}

		public int get(int index) {
			return key[index];
		}

		public int size() {
			return keyCount;
		}

		public void stepSize() {
			keyCount++;
		}
		
		public void setSize(int count){
			this.keyCount = count;
		}

		public int[] getKeys() {
			return key;
		}
		
		public boolean isLeaf(){
			return b;
		}
		public BNode getNode(int i) {
			return node[i];
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("BNode [b=").append(b).append(", key=[");
			for(int i = 0;i<keyCount;i++){
				sb.append(key[i]);
				if(i<keyCount-1){
					sb.append(",");
				}
			}
			sb.append("]");
			return sb.toString();
		}
	}

	public BTree() {
		root = new BNode(0,true);
	}

	public void insert(int key) {
		BNode n = insert(key,root);
		if(n==null){return;}
		BNode t = new BNode(2);
		t.add(0, root.get(0), root);
		t.add(1, n.get(0),n);
		root = t;
	}
	
	private BNode insert(int key, BNode node) {
		int i;
		if(node.isLeaf()){
			for (i = 0; i < node.size(); i++) {
				if (compare(key, node.get(i))) {
					break;
				}
			}
			for (int j = node.size(); j>i; j--) {
				node.swap(j, j-1);
			}
			node.insert(i, key);
		}else{
			BNode n = null;
			for(i = 0;i< node.size();i++){
				if (i==node.size()-1 || compare(key, node.get(i+1))) {
					n = insert(key, node.getNode(i));
					if(n==null){return null;}
					break;
				}
			}
			for(int j = node.size();j>i+1;j--){
				node.add(j, node.get(j-1), node.getNode(j-1));
			}
			node.insert(i+1, n.get(0), n);
		}
		if (node.size() >= M) {
			return split(node,node.isLeaf());
		}
		return null;
	}

	private boolean compare(int one, int two) {
		return one < two;
	}
	
	private BNode split(BNode node,boolean b) {
		int mond = (M >> 1) + ((M % 2 != 0) ? 1 : 0);
		System.out.println("mond-->"+mond);
		BNode n = new BNode(M >> 1,b);
		for (int i = 0; i < M >> 1; i++) {
			n.add(i, node.get(mond + i), node.getNode(mond + i));
		}
		node.setSize(mond);
		return n;
	}
	
	public String toString(){
		BNode n = root;
		toString(n, 0);
		return "";
	}
	
	public void toString(BNode n,int step){
		for(int i=0;i<step;i++){
			System.out.print("－");
		}
		if(n.isLeaf()){
			System.out.println(n.toString());
		}else{
			for(int i = 0;i<n.size();i++){
				//System.out.println("i="+i);
//				if(i!=0){
					System.out.println(n.get(i));
//				}
				toString(n.getNode(i), step+2);
			}
		}
	}

	public static void main(String[] args) {
		Random random = new Random();
		BTree tree = new BTree();
		for (int i = 0; i < 20; i++) {
			int nextInt = random.nextInt(10000);
			// System.out.println(nextInt);
			tree.insert(nextInt);
		}
		System.out.println("-----------------");
		tree.toString();
	}
}
