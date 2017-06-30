package com.gof.pattern.observer;

public interface Observer {

	public <T extends Observer> void update(NumberGenerator<T> numberGenerator);
}
