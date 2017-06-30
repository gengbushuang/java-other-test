package com.gof.pattern.observer;

import java.util.concurrent.TimeUnit;

public class GraphObserver implements Observer {

	@Override
	public <T extends Observer> void update(NumberGenerator<T> numberGenerator) {
		System.out.print("GraphObserver");
		int count = numberGenerator.getNumber();
		for(int i = 0;i<count;i++){
			System.out.print("*");
		}
		System.out.println("");
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
