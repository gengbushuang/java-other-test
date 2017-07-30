package com.scripting.language.analyzer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.scripting.language.Token;
import com.scripting.language.exception.ParseException;

public class Lexer {

	public static String regexPat = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")|[A-Z_a-z][A-Z_a-z_0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
	private Pattern pattern = Pattern.compile(regexPat);

	private ArrayList<Token> queue = new ArrayList<Token>();

	private boolean hasMore;
	private LineNumberReader reader;

	public Lexer(Reader r) {
		hasMore = true;
		reader = new LineNumberReader(r);
	}

	public Token read() throws ParseException {
		if (fillQueue(0)) {
			return queue.remove(0);
		} else {
			return Token.EOF;
		}
	}

	public Token peek(int i) throws ParseException {
		if (fillQueue(i)) {
			return queue.get(i);
		} else {
			return Token.EOF;
		}
	}

	private boolean fillQueue(int i) throws ParseException {
		while (i >= queue.size()) {
			if (hasMore) {
				readLine();
			} else {
				return false;
			}
		}
		return true;
	}

	protected void readLine() throws ParseException {
		String line;
		try {
			// 流读取文本一行内容
			line = reader.readLine();
		} catch (IOException e) {
			throw new ParseException(e);
		}
		// 读到末尾就为null
		if (line == null) {
			hasMore = false;
			return;
		}
		// 读取到第几行数
		int lineNo = reader.getLineNumber();
		// 正则匹配每一行内容
		Matcher matcher = pattern.matcher(line);
		// useTransparentBounds设置此匹配器区域边界的透明度。
		// \b
		// 匹配一个字边界，即字与空格间的位置。\\bcar\\b。匹配"Madagascar is best seen by car or bike."
		// true匹配器可以感知\\bcar\\b区域外字符，因此第一个Madagascar不被匹配。
		// false匹配器无法感知\\bcar\\b区域外字符，因此第一个Madagascar被匹配。
		// useAnchoringBounds设置匹配器区域界限的定位。
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0;
		int endPos = line.length();
		while (pos < endPos) {
			matcher.region(pos, endPos);
			if (matcher.lookingAt()) {
				addToken(lineNo, matcher);
				pos = matcher.end();
			} else {
				throw new ParseException("bad token at line " + lineNo);
			}
		}
	}

	protected void addToken(int lineNo, Matcher matcher) {
		// 正则第一个括号((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")|[A-Z_a-z][A-Z_a-z_0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})
		String m = matcher.group(1);
		if (m != null) {// 没有空白
			// 正则第二个括号(//.*)
			if (matcher.group(2) == null) {
				Token token;
				if (matcher.group(3) != null) {// 正则第三个括号([0-9]+)
					token = new NumToken(lineNo, Integer.parseInt(m));
					// System.out.println("3 is Number " + m);
				} else if (matcher.group(4) != null) {// 正则第四个括号(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")
				// System.out.println("4 is String " + m);
					token = new StrToken(lineNo, toStringLiteral(m));
				} else {// 正则剩下的[A-Z_a-z][A-Z_a-z_0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct}
				// System.out.println("4 is Identifier " + m);
					token = new IdToken(lineNo, m);
				}
				queue.add(token);
			}
		}
	}

	protected String toStringLiteral(String s) {
		StringBuilder sb = new StringBuilder();
		int len = s.length() - 1;
		for (int i = 1; i < len; i++) {
			char c = s.charAt(i);
			if (c == '\\' && i + 1 < len) {
				int c2 = s.charAt(i + 1);
				if (c2 == '"' || c2 == '\\') {
					c = s.charAt(++i);
				} else if (c2 == 'n') {
					++i;
					c = '\n';
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
