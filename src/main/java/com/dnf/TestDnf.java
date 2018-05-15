package com.dnf;

import java.util.Arrays;

public class TestDnf {
	// 0 or 1 ->0
	// 2 or 3 ->1
	// 4 or 5 ->2
	// 6 or 1 ->3
	// 3 or 6 ->4
	// 3 or 0 or 1 ->5
	// 0 or 5 ->6
	String[] docs = { "(age in {3} and state in {NY}) or (state in {CA} and gender in {M})",
			"(age in {3} and gender in {F}) or (state not in {CA, NY})",
			"(age in {3} and gender in {M} and state not in {CA}) or (state in {CA} and gender in {F})",
			"(age in {3, 4}) or (state in {CA} and gender in {M})", "(state not in {CA, NY}) or (age in {3, 4})",
			"(state not in {CA, NY}) or (age in {3} and state in {NY}) or (state in {CA} and gender in {M})",
			"(age in {3} and state in {NY}) or (state in {CA} and gender in {F})" };
	DnfBuild build = new DnfBuild();

	public void addDocs() {
		for (int i = 0; i < docs.length; i++) {
			build.AddDoc("doc" + i, "1" + i, docs[i]);
		}
		System.out.println("完成");
		Cond[] conds = { new Cond("state", "CA"), new Cond("age", "4"), new Cond("gender", "M") };
		for (int i = 0; i < 1; i++) {
			long n = System.currentTimeMillis();
			int[] search = build.search(conds);
			System.out.println(System.currentTimeMillis() - n);
			System.out.println(Arrays.toString(search));
		}
	}

	public static void main(String[] args) {
		new TestDnf().addDocs();
	}

}
