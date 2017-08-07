package com.scripting.language.ast;
import static com.scripting.language.ast.Parser.rule;

/* primary    : "(" expr ")" | NUMBER | IDENTIFIER | STRING
 * factor     : "-" primary | primary
 * expr       : factor { OP factor }
 * block      : "{" [ statement ] {(";" | EOL) [ statement ]} "}"
 * simple     : expr
 * statement  : "if" expr block [ "else" block ]
 *            | "while" expr block
 *            | simple
 * program    : [ statement ] (";" | EOL)
 * 
 */

public class BasicParser {

	Parser expr0 = rule();
//	Parser primary = rule(Primar)
	
	public static void main(String[] args) {
		new BasicParser();
	}
}
