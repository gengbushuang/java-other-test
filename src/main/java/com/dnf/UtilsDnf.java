package com.dnf;

public class UtilsDnf {

	/**
	 * 跳过字符串前面空格位置
	 * @param str
	 * @param n
	 * @return
	 */
	public static int skipSpace(String str, int n) {
		int i = n;
		for (; i < str.length();) {
			if (str.charAt(i) != '\040') {
				break;
			}
			i++;
		}
		return i;
	}
	
	public static int skipString(String str, int i) {
		if (i > str.length()) {
			return str.length();
		}
		int j = i+1;
		for (; j < str.length(); j++) {
			switch (str.charAt(j)) {
			case '\040'://空格
				return j;
			case '\54'://逗号
				return j;
			case '\173'://开花括号
				return j;
			case '\175'://闭花括号
				return j;
			}
		}
		return j;
	}

	public static Patr<String,Integer> getString(String str,int i) {
		int endIndex = skipString(str, i);
		return new Patr<String,Integer>(str.substring(i, endIndex),endIndex);
	}
	
	public static void main(String[] args) {
		String s = "sfsdf";
		
		int t = UtilsDnf.skipSpace(s, 0);
		System.out.println(t);
	}
}
