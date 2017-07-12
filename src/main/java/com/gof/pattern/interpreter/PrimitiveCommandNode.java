package com.gof.pattern.interpreter;

/**
 * <primitive command> ::= go | right | left
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月12日 下午6:54:20
 */
public class PrimitiveCommandNode extends Node {

	private String name;

	@Override
	public void parse(Context context) throws ParseException {
		name = context.currentToken();
		context.skipToken(name);
		if (!name.equals("go") && !name.equals("right") && !name.equals("left")) {
			throw new ParseException(name + " is undefined");
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
