package com.gof.pattern.interpreter;

/**
 * <program> ::= program <command list>
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月12日 下午6:40:31
 */
public class ProgramNode extends Node {

	private Node commandListNode;

	@Override
	public void parse(Context context) throws ParseException {
		context.skipToken("program");
		commandListNode = new CommandListNode();
		commandListNode.parse(context);
	}

	@Override
	public String toString() {
		return "[program=" + commandListNode + "]";
	}
}
