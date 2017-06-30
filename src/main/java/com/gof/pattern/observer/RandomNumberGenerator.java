package com.gof.pattern.observer;

import java.util.Random;

public class RandomNumberGenerator extends NumberGenerator<Observer> {
	//随机数生成器
	private Random random = new Random();

	private int number;
	
	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public void execute() {
		for (int i = 0; i < 20; i++) {
			number = random.nextInt(50);
			notifyObservers();
			System.out.println("-------------------------");
		}
	}

}
