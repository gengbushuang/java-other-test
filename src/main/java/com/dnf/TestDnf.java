package com.dnf;

public class TestDnf {

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

		Cond[] conds = { new Cond("zone", "1"), new Cond("state", "CA"), new Cond("age", "4"),
				new Cond("gender", "M") };
		long n = System.currentTimeMillis();
		int[] search = build.search(conds);
		System.out.println(System.currentTimeMillis() - n);
	}

	public static void main(String[] args) {
		new TestDnf().addDocs();
	}

}
