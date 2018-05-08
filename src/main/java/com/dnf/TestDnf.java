package com.dnf;

public class TestDnf {

	String [] docs = {
			"(age in {3} and state in {NY}) or (state in {CA} and gender in {M})",
			"(age in {3} and gender in {F}) or (state not in {CA, NY})",
			"(age in {3} and gender in {M} and state not in {CA}) or (state in {CA} and gender in {F})",
			"(age in {3, 4}) or (state in {CA} and gender in {M})",
			"(state not in {CA, NY}) or (age in {3, 4})",
			"(state not in {CA, NY}) or (age in {3} and state in {NY}) or (state in {CA} and gender in {M})",
			"(age in {3} and state in {NY}) or (state in {CA} and gender in {F})"	
	};
	
	public void addDocs() {
		for (int i = 0; i < docs.length; i++) {
			System.out.println("doc"+i);
			System.out.println(i);
			System.out.println(docs[i]);
		}
	}
	
	public static void main(String[] args) {
		new TestDnf().addDocs();
	}
	
}
