package com.gof.pattern.builder;
/**
 * 
 * Builder(建造者)
 * Builder角色负责定义用于生成实例的接口。Builder角色中准备了用于生成实例的方法。
 * 示例中，由Builder类扮演此角色。
 * 
 * ConcreteBuilder(具体的建造者)
 * ConcreteBuilder角色是负责实现Builder角色的接口的类。
 * 这是定义了在生成实例是实际被调用的方法。此外，
 * 在ConcreteBuilder角色中还定义了获取最终生成结果的方法。示例中，
 * 由TextBuilder类和HTMLBuilder类扮演此角色。
 * 
 * Director(监工)
 * Director角色负责使用Builder角色的接口来生成实例。它并不依赖于ConcreteBuilder角色。
 * 为了确保不论ConcreteBuilder角色是如何被定义的，Director角色都能正常工作，
 * 它只调用在Builder角色中被定义的方法。示例中，由Director类扮演此角色。
 * 
 * Cilent(使用者)
 * 该角色使用了Builder模式(Builder模式并不包含Client角色)。示例中，由TestMain类扮演。
 * 
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年6月20日 上午10:14:31
 */
public class TestMain {
	public static void main(String[] args) {
		TextBuilder textBuilder = new TextBuilder();
		Director director = new Director(textBuilder);
		director.construct();
		String result = textBuilder.getResult();
		System.out.println(result);
	}
}
