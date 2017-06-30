package com.gof.pattern.observer;

import java.util.concurrent.TimeUnit;

public class DigitObserver implements Observer {

	@Override
	public <T extends Observer> void update(NumberGenerator<T> numberGenerator) {
		System.out.println("DigitObserver:" + numberGenerator.getNumber());
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
