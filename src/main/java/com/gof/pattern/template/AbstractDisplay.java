package com.gof.pattern.template;

public abstract class AbstractDisplay {

	public abstract void open();

	public abstract void print();

	public abstract void close();

	public void display() {
		open();
		for (int i = 0; i < 5; i++) {
			print();
		}
		close();
	}
}
