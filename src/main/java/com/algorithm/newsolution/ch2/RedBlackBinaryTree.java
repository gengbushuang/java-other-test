package com.algorithm.newsolution.ch2;

public class RedBlackBinaryTree {
	enum Color {
		RED, BLACK
	}

	class Node {
		Color color;
		Node left;
		Node right;
		Node parent;
		int key;

		public Node(int key) {
			this.key = key;
			this.left = right = parent = null;
			this.color = Color.RED;
		}
	}

}
