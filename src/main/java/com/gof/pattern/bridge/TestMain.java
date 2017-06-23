package com.gof.pattern.bridge;
/**
 * Abstraction (抽象化)
 * 该角色位于“类的功能层次结构”的最上层。它使用Implementor角色的方法定义了基本的功能。
 * 该角色中保存了Implementor角色的实例。示例中，有Display类扮演此角色。
 * 
 * RefinedAbstraction (改善后的抽象化)
 * 在Abstraction角色的基础上增加了新功能的角色。示例中，由CountDisplay类扮演此角色。
 * 
 * Implementor (实现者)
 * 该角色位于“类的实现层此结构”的最上层。它定义了用于实现Abstraction角色的接口的方法。
 * 示例中，由DisplayImpl类扮演此角色。
 * 
 * ConcreteImplementro (具体实现者)
 * 该角色负责实现在Implementor角色中定义的接口。示例中，
 * 由StringDisplayImpl类扮演此角色。
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年6月23日 下午4:33:51
 */
public class TestMain {
	
	public static void main(String[] args) {
		Display d1 = new Display(new StringDisplayImpl("Hello,China."));
		Display d2 = new CountDisplay(new StringDisplayImpl("Hello,World."));
		CountDisplay d3 = new CountDisplay(new StringDisplayImpl("Hello,Universe."));
		d1.display();
		d2.display();
		d3.display();
		d3.multiDisplay(5);
	}
}
