package com.loadclass;

public class InterMain {

	public static void main(String[] args) {
		InterClass<Inter1> interClass1 = new InterClass<Inter1>(Inter1.class);
		interClass1.add();
		
		InterClass<Inter2> interClass2 = new InterClass<Inter2>(Inter2.class);
		interClass2.add();
	}
}
