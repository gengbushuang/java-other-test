package com.scripting.language.ast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.scripting.language.ASTree;
import com.scripting.language.Token;
import com.scripting.language.analyzer.Lexer;
import com.scripting.language.exception.ParseException;

/**
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年8月6日 下午3:43:42
 */
public class Parser {

	protected static abstract class Element {
		protected abstract void parse(Lexer lexer, List<ASTree> res) throws ParseException;

		protected abstract boolean match(Lexer lexer) throws ParseException;
	}

	protected static class Tree extends Element {
		protected Parser parser;

		public Tree(Parser p) {
			parser = p;
		}

		@Override
		protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
			res.add(parser.parse(lexer));
		}

		@Override
		protected boolean match(Lexer lexer) throws ParseException {
			return parser.match(lexer);
		}

	}

	protected static class OrTree extends Element {

		protected Parser[] parsers;

		protected OrTree(Parser[] ps) {
			parsers = ps;
		}

		@Override
		protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
			Parser p = choose(lexer);
			if (p == null) {
				throw new ParseException(lexer.peek(0));
			} else {
				res.add(p.parse(lexer));
			}
		}

		@Override
		protected boolean match(Lexer lexer) throws ParseException {
			return choose(lexer) != null;
		}

		protected Parser choose(Lexer lexer) throws ParseException {
			for (Parser p : parsers) {
				if (p.match(lexer)) {
					return p;
				}
			}
			return null;
		}

		protected void insert(Parser p) {
			Parser[] newParsers = new Parser[parsers.length + 1];
			newParsers[0] = p;
			System.arraycopy(parsers, 0, newParsers, 1, parsers.length);
			parsers = newParsers;
		}

	}

	protected static class Repeat extends Element {

		protected Parser parser;
		protected boolean onlyOnce;

		protected Repeat(Parser p, boolean once) {
			parser = p;
			onlyOnce = once;
		}

		@Override
		protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
			while (parser.match(lexer)) {
				ASTree t = parser.parse(lexer);
				if (t.getClass() != ASTList.class || t.childNumber() > 0) {
					res.add(t);
				}
				if (onlyOnce) {
					break;
				}
			}

		}

		@Override
		protected boolean match(Lexer lexer) throws ParseException {
			return parser.match(lexer);
		}

	}

	protected static abstract class AToken extends Element {
		protected Factory factory;

		protected AToken(Class<? extends ASTLear> type) {
			if (type == null) {
				type = ASTLear.class;
			}
			// Token构造方法的工厂
			factory = Factory.get(type, Token.class);
		}

		@Override
		protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
			Token t = lexer.read();
			if (test(t)) {
				ASTree lear = factory.make(t);
				res.add(lear);
			} else {
				throw new ParseException(t);
			}
		}

		@Override
		protected boolean match(Lexer lexer) throws ParseException {
			return test(lexer.peek(0));
		}

		protected abstract boolean test(Token t);

	}

	protected static class IdToken extends AToken {

		HashSet<String> reserved;

		protected IdToken(Class<? extends ASTLear> type, HashSet<String> r) {
			super(type);
			reserved = r != null ? r : new HashSet<String>();
		}

		@Override
		protected boolean test(Token t) {
			return t.isIdentifier() && !reserved.contains(t.getText());
		}

	}

	protected static class NumToken extends AToken {

		protected NumToken(Class<? extends ASTLear> type) {
			super(type);
		}

		@Override
		protected boolean test(Token t) {
			return t.isNumber();
		}

	}

	protected static class StrToken extends AToken {

		protected StrToken(Class<? extends ASTLear> type) {
			super(type);
		}

		@Override
		protected boolean test(Token t) {
			return t.isString();
		}
	}

	protected static class Lear extends Element {

		protected String[] tokens;

		protected Lear(String[] pat) {
			tokens = pat;
		}

		@Override
		protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
			Token t = lexer.read();
			if (t.isIdentifier()) {
				for (String token : tokens) {
					if (token.equals(t.getText())) {
						find(res, t);
						return;
					}
				}
			}
			if (tokens.length > 0) {
				throw new ParseException(tokens[0] + " expected.", t);
			} else {
				throw new ParseException(t);
			}
		}

		@Override
		protected boolean match(Lexer lexer) throws ParseException {
			Token t = lexer.peek(0);
			if (t.isIdentifier()) {
				for (String token : tokens) {
					if (token.equals(t.getText())) {
						return true;
					}
				}
			}
			return false;
		}

		protected void find(List<ASTree> res, Token t) {
			res.add(new ASTLear(t));
		}

	}

	protected static class Skip extends Lear {

		protected Skip(String[] pat) {
			super(pat);
		}

		protected void find(List<ASTree> res, Token t) {
		}

	}

	public static class Precedence {
		int value;
		boolean leftAssoc;

		public Precedence(int v, boolean a) {
			value = v;
			leftAssoc = a;
		}
	}

	public static class Operators extends HashMap<String, Precedence> {
		public static boolean LEFT = true;
		public static boolean RIGHT = false;

		public void add(String name, int prec, boolean leftAssoc) {
			put(name, new Precedence(prec, leftAssoc));
		}
	}

	protected static class Expr extends Element {

		protected Factory factory;
		protected Operators ops;
		protected Parser factor;

		public Expr(Class<? extends ASTree> cla, Parser exp, Operators map) {
			factory = Factory.getForASTList(cla);
			ops = map;
			factor = exp;
		}

		@Override
		protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
			ASTree right = factor.parse(lexer);
			Precedence prec;
			while ((prec = nextOperator(lexer)) != null) {
				right = doShift(lexer, right, prec.value);
			}
			res.add(right);
		}

		private ASTree doShift(Lexer lexer, ASTree left, int prec) throws ParseException {
			ArrayList<ASTree> list = new ArrayList<>();
			list.add(left);
			list.add(new ASTLear(lexer.read()));
			ASTree right = factor.parse(lexer);
			Precedence next;
			while ((next = nextOperator(lexer)) != null && rightIsExpr(prec, next)) {
				right = doShift(lexer, right, next.value);
			}
			list.add(right);
			return factory.make(list);
		}

		private static boolean rightIsExpr(int prec, Precedence nextPrec) {
			if (nextPrec.leftAssoc) {
				return prec < nextPrec.value;
			} else {
				return prec <= nextPrec.value;
			}
		}

		private Precedence nextOperator(Lexer lexer) throws ParseException {
			Token t = lexer.peek(0);
			if (t.isIdentifier()) {
				return ops.get(t.getText());
			} else {
				return null;
			}
		}

		@Override
		protected boolean match(Lexer lexer) throws ParseException {
			return factor.match(lexer);
		}

	}

	// ////////////////////////////
	public static final String factoryName = "create";

	protected static abstract class Factory {

		protected abstract ASTree make0(Object arg) throws Exception;

		protected ASTree make(Object arg) {
			try {
				return make0(arg);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		protected static Factory getForASTList(Class<? extends ASTree> cla) {
			Factory f = get(cla, List.class);
			if (f == null) {
				f = new Factory() {
					@Override
					protected ASTree make0(Object arg) throws Exception {
						List<ASTree> results = (List<ASTree>) arg;
						if (results.size() == 1) {
							return results.get(0);
						} else {
							return new ASTList(results);
						}
					}
				};
			}
			return f;
		}

		protected static Factory get(Class<? extends ASTree> cla, Class<?> argType) {
			if (cla == null) {
				return null;
			}
			try {
				// ASTree类，带create方法名argType参数方法对象
				final Method m = cla.getMethod(factoryName, new Class<?>[] { argType });
				// 生成一个ASTree带create方法名argType参数对象的工厂
				return new Factory() {
					@Override
					protected ASTree make0(Object arg) throws Exception {
						return (ASTree) m.invoke(null, arg);
					}
				};
			} catch (NoSuchMethodException e) {
				try {
					// ASTree带参数的构造器对象
					Constructor<? extends ASTree> c = cla.getConstructor(argType);
					// 生成一个ASTree带参数的构造器对象工厂
					return new Factory() {
						@Override
						protected ASTree make0(Object arg) throws Exception {
							return c.newInstance(arg);
						}
					};
				} catch (NoSuchMethodException e1) {
					throw new RuntimeException(e1);
				}
			}
		}
	}

	// ////////////////////////////
	protected List<Element> elements;
	protected Factory factory;

	public Parser(Class<? extends ASTree> cla) {
		reset(cla);
	}

	protected Parser(Parser p) {
		elements = p.elements;
		factory = p.factory;
	}

	public ASTree parse(Lexer lexer) throws ParseException {
		ArrayList<ASTree> results = new ArrayList<>();
		for (Element e : elements) {
			e.parse(lexer, results);
		}
		return factory.make(results);
	}

	protected boolean match(Lexer lexer) throws ParseException {
		if (elements.size() == 0) {
			return true;
		} else {
			Element e = elements.get(0);
			return e.match(lexer);
		}
	}

	public static Parser rule() {
		return rule(null);
	}

	public static Parser rule(Class<? extends ASTree> cla) {
		return new Parser(cla);
	}

	public Parser reset() {
		elements = new ArrayList<Element>();
		return this;
	}

	public Parser reset(Class<? extends ASTree> cla) {
		elements = new ArrayList<>();
		factory = Factory.getForASTList(cla);
		return this;
	}

	public Parser number() {
		return number(null);
	}

	public Parser number(Class<? extends ASTLear> cla) {
		elements.add(new NumToken(cla));
		return this;
	}

	public Parser identifer(HashSet<String> reserved) {
		return identifer(null, reserved);
	}

	public Parser identifer(Class<? extends ASTLear> cla, HashSet<String> reserved) {
		elements.add(new IdToken(cla, reserved));
		return this;
	}

	public Parser string() {
		return string(null);
	}

	public Parser string(Class<? extends ASTLear> cla) {
		elements.add(new StrToken(cla));
		return this;
	}

	public Parser token(String... pat) {
		elements.add(new Lear(pat));
		return this;
	}

	public Parser sep(String... pat) {
		elements.add(new Skip(pat));
		return this;
	}

	public Parser ast(Parser p) {
		elements.add(new Tree(p));
		return this;
	}

	public Parser or(Parser... p) {
		elements.add(new OrTree(p));
		return this;
	}

	public Parser maybe(Parser p) {
		Parser p2 = new Parser(p);
		p2.reset();
		elements.add(new OrTree(new Parser[] { p, p2 }));
		return this;
	}

	public Parser option(Parser p) {
		elements.add(new Repeat(p, true));
		return this;
	}

	public Parser reqeat(Parser p) {
		elements.add(new Repeat(p, false));
		return this;
	}

	public Parser expression(Parser subexp, Operators operators) {
		elements.add(new Expr(null, subexp, operators));
		return this;
	}

	public Parser expression(Class<? extends ASTree> cla, Parser subexp, Operators operators) {
		elements.add(new Expr(cla, subexp, operators));
		return this;
	}

	public Parser insertChoice(Parser p) {
		Element e = elements.get(0);
		if (e instanceof OrTree) {
			((OrTree) e).insert(p);
		} else {
			Parser otherwise = new Parser(this);
			reset(null);
			or(p, otherwise);
		}
		return this;
	}
}
