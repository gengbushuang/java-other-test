package com.data_structure.tree;

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

	private static class BinaryNode<AnyType> {

		AnyType theElement;
		BinaryNode<AnyType> left;
		BinaryNode<AnyType> right;

		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> left, BinaryNode<AnyType> right) {
			this.theElement = theElement;
			this.left = left;
			this.right = right;
		}
	}

	private BinaryNode<AnyType> root;

	public BinarySearchTree() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	private boolean contains(AnyType x, BinaryNode<AnyType> b) {
		if (b == null) {
			return false;
		}
		int result = x.compareTo(b.theElement);
		if (result < 0) {
			return contains(x, b.left);
		} else if (result > 0) {
			return contains(x, b.right);
		} else {
			return true;
		}
	}

	public void insert(AnyType a) {
		root = insert(a, root);
	}

	public BinaryNode<AnyType> insert(AnyType a, BinaryNode<AnyType> b) {
		if (b == null) {
			return new BinaryNode<AnyType>(a);
		}
		int result = a.compareTo(b.theElement);
		if (result < 0) {
			b.left = insert(a, b.left);
		} else if (result > 0) {
			b.right = insert(a, b.right);
		}
		return b;
	}

	public void remove(AnyType a) {

	}

	public BinaryNode<AnyType> remove(AnyType a, BinaryNode<AnyType> b) {
		if (b == null) {
			return b;
		}
		int result = a.compareTo(b.theElement);
		if (result < 0) {
			b.left = remove(a, b.left);
		} else if (result > 0) {
			b.right = remove(a, b.right);
		} else if (b.left != null && b.right != null) {
			b.theElement = findMin(b.right).theElement;
			b.right = remove(b.theElement, b.right);
		} else {
			b= (b.left!=null)?b.left:b.right; 
		}
		return b;
	}

	public AnyType findMin() {
		return findMin(root).theElement;
	}

	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> b) {
		if (b == null) {
			return null;
		}
		for (; b.left != null;) {
			b = b.left;
		}
		return b;
	}

	public AnyType findMax() {
		return findMax(root).theElement;
	}

	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> b) {
		if (b == null) {
			return null;
		}
		for (; b.right != null;) {
			b = b.right;
		}
		return b;

	}

}
