package com.gof.pattern.interpreter;

/**
 * <repeat command> ::= repeat <number> <command list>
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月12日 下午6:53:35
 */
public class RepeatCommandNode extends Node {

	private int number;

	private Node commandListNode;

	@Override
	public void parse(Context context) throws ParseException {
		context.skipToken("repeat");
		number = context.currentNumber();
		context.nextToken();
		commandListNode = new CommandListNode();
		commandListNode.parse(context);
	}

	@Override
	public String toString() {
		return "[repeat=" + number + " " + commandListNode + "]";
	}

}
