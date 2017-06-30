package com.gof.pattern.observer;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class NumberGenerator<T extends Observer> {
	// 保存Observer
	private ArrayList<T> observers = new ArrayList<T>();

	// 注册Observer
	public void addObserver(T t) {
		observers.add(t);
	}

	// 删除Observer
	public void deleteObserver(T t) {
		observers.remove(t);
	}

	// 向Observer发送通知
	public void notifyObservers() {
		Iterator<T> iterator = observers.iterator();
		for (; iterator.hasNext();) {
			T t = iterator.next();
			t.update(this);
		}
	}

	// 获取数值
	public abstract int getNumber();

	// 生成数值
	public abstract void execute();
}
