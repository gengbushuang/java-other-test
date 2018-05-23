package com.data_structure.tree;

/**
 * 单独给星期时间的写的
 * 
 * @author gbs
 *
 */
public class RedBlackTreeUtils {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	class EntryNode {
		EntryNode left, right, parent;
		int key, value;
		boolean color = BLACK;

		EntryNode(int key, int value, EntryNode parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

	private transient EntryNode root;
	
	public void put(int key, int value) {
		EntryNode t = root;
		if (t == null) {
			root = new EntryNode(key, value, null);
			return;
		}

		EntryNode parent = null;
		int result = 0;
		while (t != null) {
			parent = t;
			result = key - t.key;
			if (result > 0) {
				t = t.right;
			} else if (result < 0) {
				t = t.left;
			} else {
				t.setValue(value);
				return;
			}
		}

		EntryNode newNode = new EntryNode(key, value, parent);
		if (result > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		fixAfterInsertion(newNode);
	}

	public int get(int key) {
		EntryNode p = getEntry(key);
		return p == null ? -1 : p.value;
	}

	EntryNode getEntry(int key) {
		EntryNode p = root;
		int result = 0;
		while (p != null) {
			result = key - p.key;
			if (result > 0) {
				p = p.right;
			} else if (result < 0) {
				p = p.left;
			} else {
				return p;
			}
		}
		return null;
	}

	// 性质1. 节点是红色或黑色
	// 性质2. 根是黑色
	// 性质3. 所有叶子都是黑色
	// 性质4. 从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点
	// 性质5. 从每个叶子到根的所有路径上不能有两个连续的红色节点
	private void fixAfterInsertion(EntryNode newNode) {
		// 新插入的节点为红色
		newNode.color = RED;
		//
		while (newNode != null && newNode != root && newNode.parent.color == RED) {
			if (getParent(newNode) == getLeft(getParent(getParent(newNode)))) {
				EntryNode y = getRight(getParent(getParent(newNode)));
				if (getColor(y) == RED) {
					setColor(getParent(newNode), BLACK);
					setColor(y, BLACK);
					setColor(getParent(getParent(newNode)), RED);
					newNode = getParent(getParent(newNode));
				} else {
					if (newNode == getRight(getParent(newNode))) {
						newNode = getParent(newNode);
						rotateLeft(newNode);
					}
					setColor(getParent(newNode), BLACK);
					setColor(getParent(getParent(newNode)), RED);
					rotateRight(getParent(getParent(newNode)));
				}
			} else {
				EntryNode y = getLeft(getParent(getParent(newNode)));
				if (getColor(y) == RED) {
					setColor(getParent(newNode), BLACK);
					setColor(y, BLACK);
					setColor(getParent(getParent(newNode)), RED);
					newNode = getParent(getParent(newNode));
				} else {
					if (newNode == getLeft(getParent(newNode))) {
						newNode = getParent(newNode);
						rotateRight(newNode);
					}
					setColor(getParent(newNode), BLACK);
					setColor(getParent(getParent(newNode)), RED);
					rotateLeft(getParent(getParent(newNode)));
				}
			}
		}

		root.color = BLACK;
	}

	private void rotateLeft(EntryNode newNode) {
		if (newNode != null) {
			EntryNode r = newNode.right;

			newNode.right = r.left;
			if (r.left != null) {
				r.left.parent = newNode;
			}

			r.parent = newNode.parent;
			if (newNode.parent == null) {
				root = r;
			} else if (newNode.parent.left == newNode) {
				newNode.parent.left = r;
			} else {
				newNode.parent.right = r;
			}

			r.left = newNode;
			newNode.parent = r;
		}
	}

	private void rotateRight(EntryNode newNode) {
		if (newNode != null) {
			EntryNode l = newNode.left;

			newNode.left = l.right;
			if (l.right != null) {
				l.right.parent = newNode;
			}

			l.parent = newNode.parent;
			if (newNode.parent == null) {
				root = l;
			} else if (newNode.parent.left == newNode) {
				newNode.parent.left = l;
			} else {
				newNode.parent.right = l;
			}

			l.right = newNode;
			newNode.parent = l;
		}
	}

	private EntryNode getParent(EntryNode newNode) {
		return newNode == null ? null : newNode.parent;
	}

	private EntryNode getLeft(EntryNode newNode) {
		return newNode == null ? null : newNode.left;
	}

	private EntryNode getRight(EntryNode newNode) {
		return newNode == null ? null : newNode.right;
	}

	private boolean getColor(EntryNode newNode) {
		return newNode == null ? BLACK : newNode.color;
	}

	private void setColor(EntryNode newNode, boolean c) {
		if (newNode != null) {
			newNode.color = c;
		}
	}
}
