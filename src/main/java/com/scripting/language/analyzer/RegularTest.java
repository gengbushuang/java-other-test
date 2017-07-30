package com.scripting.language.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
/**
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月29日 下午11:46:31
 */
/*
 * 
 */
public class RegularTest {

	public static void main(String[] args) {
		// \\\\-代表\\
		String str ="\\s*((//.*)|(0-9+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")|[A-Z_a-z][A-Z_a-z_0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
		String regexPat = "([A-Z_a-z][A-Z_a-z_0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
		Pattern pattern = Pattern.compile(str);
		
		String line = "int str = 1;";
		Matcher matcher = pattern.matcher(line);
		
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0;
		int end = line.length();
		while(pos<end){
			matcher.region(pos, end);
			if (matcher.lookingAt()) {
				String m = matcher.group(1);
				System.out.println("m-->" + m);
				m = matcher.group(2);
				System.out.println("m-->" + m);
				m = matcher.group(3);
				System.out.println("m-->" + m);
				pos = matcher.end();
				System.out.println("pos-->" + pos);
			}
		}
	}
}
