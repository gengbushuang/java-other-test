package com.scripting.language.ast;

import java.util.List;

import com.scripting.language.ASTree;
/*                   
 * 单词序列 13 + x * 2
 *                   +--------------+
 *                   | BinaryExper  | 
 *                   | ------------ |
 *                   |  operator=+  |
 *                   +--------------+
 *                         /  \
 *                        /    \
 *                 left  /      \  right
 *                      /        \ 
 *       +---------------+       +--------------+
 *       | NumberLiteral |       | BinaryExper  | 
 *       |---------------|       |--------------|    
 *       |     vale=3    |       | operator=*   |
 *       +---------------+       +--------------+
 *                                     /  \
 *                                    /    \
 *                             left  /      \  right
 *                                  /        \ 
 *                   +---------------+       +----------------+
 *                   |     Name      |       | NumberLiteral  | 
 *                   |---------------|       |----------------|    
 *                   |    name=x     |       |    value=2     |
 *                   +---------------+       +----------------+
 *       
 */

/**
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年8月7日 上午8:59:16
 */
public class BinaryExper extends ASTList {

	public BinaryExper(List<ASTree> children) {
		super(children);
	}

	public ASTree getLeft() {
		return this.child(0);
	}

	public String operator() {
		return ((ASTLear) this.child(1)).getToken().getText();
	}

	public ASTree getRight() {
		return this.child(2);
	}
}
