package com.gof.pattern.template;
/**
 *  AbstractClass(抽象类)
 *  AbstractClass角色不仅负责实现模板方法，还负责声明在模板方法中所使用到的抽象方法。
 *  这些抽象方法由子类ConcreteClass角色负责实现。示例中，由AbstractDisplay类扮演。
 *  
 *  ConcreteClass(具体类)
 *  该角色负责具体实现AbstractClass角色中定义的抽象方法。
 *  这里实现的方法将会在AbstractClass角色的模板方法中调用。示例中，
 *  由CharDisplay类和StringDisplay类扮演此角色。
 * 
 * 
 * 
 * @Description:TODO
 * @author gbs
 * @date 2017年6月20日 上午1:39:29
 */
public class MainTest {
	public static void main(String[] args){
		AbstractDisplay d1 = new CharDisplay('H');
		AbstractDisplay d2 = new StringDisplay("Hello,world.");
		
		d1.display();
		d2.display();
	}
}
