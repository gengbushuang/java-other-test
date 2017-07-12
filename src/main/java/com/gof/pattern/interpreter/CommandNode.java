package com.gof.pattern.interpreter;

/**
 * <command> ::= <repeat command> | <primitive command>
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月12日 下午6:52:51
 */
public class CommandNode extends Node {

	private Node node;

	@Override
	public void parse(Context context) throws ParseException {
		if (context.currentToken().equals("repeat")) {
			node = new RepeatCommandNode();
			node.parse(context);
		} else {
			node = new PrimitiveCommandNode();
			node.parse(context);
		}
	}

	@Override
	public String toString() {
		return node.toString();
	}

}
