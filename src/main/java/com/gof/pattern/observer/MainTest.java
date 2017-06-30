package com.gof.pattern.observer;

/**
 * Subject(观察对象)
 * Subject角色表示观察对象。Subject角色定义了注册观察者和删除观察者的方法。
 * 此外，它还声明了“获取现在的状态”的方法。示例中，由NumberGenerator类扮演此角色。
 * 
 * ConcreteSubject(具体的观察对象)
 * ConcreteSubject角色表示具体的被观察对象。当自身状态发生变化后，
 * 它会通知所有已经注册的Observer角色。示例中，有RandomNumberGenerator类扮演此角色。
 * 
 * Observer(观察者)
 * Observer角色负责接受来自Subject角色的状态变化的通知。为此，它声明了update方法。
 * 示例中，由Observer接口扮演此角色。
 * 
 * ConcreteObserver(具体的观察者)
 * ConcreteObserver角色表示具体的Observer。当它的update方法被调用后，会去获取要观察的对象的最新状态。
 * 示例中，由DigitObserver类和GraphObserver类扮演此角色。
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年6月28日 上午10:41:39
 */
public class MainTest {


	public static void main(String[] args) {
		NumberGenerator<Observer> generator = new RandomNumberGenerator();
		Observer observer1 = new DigitObserver();
		Observer observer2 = new GraphObserver();
		generator.addObserver(observer1);
		generator.addObserver(observer2);
		generator.execute();
	}
}
