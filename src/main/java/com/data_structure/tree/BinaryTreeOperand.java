package com.data_structure.tree;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinaryTreeOperand {

	class BinaryNode {
		private Object element;
		private BinaryNode left;
		private BinaryNode right;

		public BinaryNode(Object element, BinaryNode left, BinaryNode right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}

		public Object getElement() {
			return element;
		}

		public void setElement(Object element) {
			this.element = element;
		}

		public BinaryNode getLeft() {
			return left;
		}

		public void setLeft(BinaryNode left) {
			this.left = left;
		}

		public BinaryNode getRight() {
			return right;
		}

		public void setRight(BinaryNode right) {
			this.right = right;
		}

		@Override
		public String toString() {
			return "BinaryNode [element=" + element + ", left=" + left + ", right=" + right + "]";
		}
	}

	/**
	 * String expresion = "ab+cde+**";
	 * 构建成树
	 * @param expresion
	 */
	public void operand(String expresion){
		String regexPat = "[+|-|*|/]";
		Pattern pattern = Pattern.compile(regexPat);
		Stack<BinaryNode> stak = new Stack<BinaryNode>();
		char[] charOperand = expresion.toCharArray();
		for(int i = 0; i<charOperand.length;i++){
			char tmp = charOperand[i];
			Matcher matcher = pattern.matcher(String.valueOf(tmp));
			if(matcher.find()){
				System.out.println(tmp);
				BinaryNode r = stak.pop();
				BinaryNode l = stak.pop();
				BinaryNode c = new BinaryNode(tmp,l,r);
				stak.push(c);
			}else{
				stak.push(new BinaryNode(tmp,null,null));
			}
		}
	}
	
	public static void main(String[] args) {
		String expresion = "ab+cde+**";
		
		new BinaryTreeOperand().operand(expresion);
	}
}
