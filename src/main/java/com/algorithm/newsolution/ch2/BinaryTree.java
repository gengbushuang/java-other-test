package com.algorithm.newsolution.ch2;

public class BinaryTree<K, V> {

	@SuppressWarnings("hiding")
	private class Node<K, V> {
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;

		K k;
		V v;

		public Node(K _k, V _v) {
			this.k = _k;
			this.v = _v;
			this.left = this.right = this.parent = null;

		}

		public K getK() {
			return k;
		}

		public V getV() {
			return v;
		}

		@Override
		public String toString() {
			return "Node [left=" + (left!=null) + ", rigth=" + (right!=null) + ", k=" + k + ", v=" + v + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((k == null) ? 0 : k.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("rawtypes")
			Node other = (Node) obj;
			if (k == null) {
				if (other.k != null)
					return false;
			} else if (!k.equals(other.k))
				return false;
			return true;
		}

	}

	private Node<K, V> root;

	/**
	 * 插入 如果树为空，创建一个叶子节点，令该节点的key=k; 如果k小于根节点的key，将它插入到左子树中;
	 * 如果k大于根节点的key，将它插入到右子树中;
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param key
	 * @param value
	 */
	public void insert(K key, V value) {
		if (key == null) {
			return;
		}
		Node<K, V> newNode = createLeaf(key, value);
		if (root == null) {
			root = newNode;
			return;
		}
		Node<K, V> p = root;
		Node<K, V> c = null;
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) key;
		int result;
		while (p != null) {
			result = k.compareTo(p.getK());
			if (result < 0) {
				c = p.left;
			} else if (result > 0) {
				c = p.right;
			} else {
				p.k = newNode.k;
				p.v = newNode.v;
				newNode = null;
				return;
			}
			if (c == null) {
				c = newNode;
				if (result < 0) {
					p.left = c;
				} else if (result > 0) {
					p.right = c;
				}
				c.parent = p;
				return;
			}
			p = c;
		}
	}

	/**
	 * 如果树为空，查找失败
	 * 如果节点的key等于查找的值，查找成功，返回根节点作为结果;
	 * 如果查找值小于根节点的KEY，继续在左子树中递归查找；
	 * 如果查找值大于根节点的KEY，继续在右子树中递归查找；
	 * @Description: TODO
	 * @author gbs
	 * @param key
	 * @return
	 */
	public V getValue(K key) {
		if (root == null || key == null) {
			return null;
		}

		BinaryTree<K, V>.Node<K, V> node = findNode(key);
		if (node != null) {
			return node.getV();
		}
		return null;
	}
	/**
	 * 删除
	 * @Description: TODO 这个还是有点问题，到时候在看看
	 * @author gbs
	 * @param key
	 */
	public void delete(K key){
		if (key == null) {
			return;
		}
		Node<K, V> p = root;
		Node<K, V> c = p;
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) key;
		int result;
		int presult = 0;
		boolean b = true;
		while (c != null) {
			result = k.compareTo(c.getK());
			if (result < 0) {
				presult = result;
				p = c;
				c = p.left;
			} else if (result > 0) {
				presult = result;
				p = c;
				c = p.right;
			} else {
				b = false;
				break;
			}
		}
		
		if (b) {
			return;
		}
		
		if (c.left == null) {
			c = c.right;
		} else if (c.right == null) {
			c = c.left;
		} else {
			Node<K, V> findMinp = c;
			Node<K, V> findMinc = findMinp.right;
			if (findMinc.left == null) {
				findMinp.right = null;
			} else {
				while (findMinc.left != null) {
					findMinp = findMinc;
					findMinc = findMinp.left;
				}
				findMinp.left = null;
			}
			c.k = findMinc.k;
			c.v = findMinc.v;
		}

		if (presult < 0) {
			p.left = c;
		} else if (presult > 0) {
			p.right = c;
		}else{
//			root = null;
		}
	}
	
	public V getKeyMax() {
		if (root == null) {
			return null;
		}
		BinaryTree<K, V>.Node<K, V> node = findMax(root);
		return node.getV();
	}

	public V getKeyMin() {
		if (root == null) {
			return null;
		}
		BinaryTree<K, V>.Node<K, V> node = findMin(root);
		return node.getV();
	}
	
	public Node<K, V> findMin(Node<K, V> node) {
		Node<K, V> p = node;
		while (p.left != null) {
			p = p.left;
		}
		return p;
	}
	
	public Node<K, V> findMax(Node<K, V> node) {
		Node<K, V> p = node;
		while (p.right != null) {
			p = p.right;
		}
		return p;
	}
	
	
	public Node<K, V> findNode(K key) {
		Node<K, V> p = root;
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) key;
		int result;
		while (p != null) {
			result = k.compareTo(p.getK());
			if (result < 0) {
				p = p.left;
			} else if (result > 0) {
				p = p.right;
			} else {
				return p;
			}
		}
		return null;
	}
	
	

	private Node<K, V> createLeaf(K key, V value) {
		return new Node<K, V>(key, value);
	}

	public void show(int type) {
		Node<K, V> p = root;
		show(p, type);
	}

	/**
	 * 原始插入顺序4,3,8,1,2,7,16,10,9,14 
	 * type1 为前序遍历：先访问根节点，然后访问左子树，最好访问右子树; 
	 * 4 3 1 2 8 7 16 10 9 14 
	 * type2 为中序遍历：先访问左子树，然后访问根节点，最后访问右子树; 
	 * 1 2 3 4 7 8 9 10 14 16
	 * type3 为后序遍历：先访问左子树，然后访问右子树，最好访问根节点; 
	 * 2 1 3 7 9 14 10 16 8 4
	 * 
	 * @Description: TODO
	 * @author gbs
	 * @param p
	 * @param type
	 */
	public void show(Node<K, V> p, int type) {
		if (p == null) {
			return;
		}
		if (type == 1) {
			System.out.print(p.getK() + " ");
		}
		show(p.left, type);
		if (type == 2) {
			System.out.print(p.getK() + " ");
		}
		show(p.right, type);
		if (type == 3) {
			System.out.print(p.getK() + " ");
		}
	}

	public static void main(String[] args) {
		BinaryTree<Integer, String> tree = new BinaryTree<Integer, String>();
		tree.insert(4, "4");
		tree.insert(3, "3");
//		tree.insert(8, "8");
//		tree.insert(1, "1");
//		tree.insert(2, "d");
//		tree.insert(7, "d");
//		tree.insert(16, "16");
//		tree.insert(10, "d");
//		tree.insert(9, "d");
//		tree.insert(14, "d");
		
		tree.delete(4);
		
		
		tree.show(2);
	}
}
