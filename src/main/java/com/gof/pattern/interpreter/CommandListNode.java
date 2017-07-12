package com.gof.pattern.interpreter;

import java.util.ArrayList;
/**
 * <command list> ::= <command>* end
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月12日 下午6:52:14
 */
public class CommandListNode extends Node {

	private ArrayList<Node> list = new ArrayList<>();

	@Override
	public void parse(Context context) throws ParseException {
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException("Missing 'end'");
			} else if (context.currentToken().equals("end")) {
				context.skipToken("end");
				break;
			} else {
				Node commandNode = new CommandNode();
				commandNode.parse(context);
				list.add(commandNode);
			}
		}
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
