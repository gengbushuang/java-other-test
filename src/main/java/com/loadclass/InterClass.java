package com.loadclass;


public class InterClass<T extends InterTest> {
	
	protected T t;
	
	public InterClass(Class<T> c) {
		try {
			this.t = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	

	public void add() {
		t.add();
	}
}
