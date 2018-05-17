package com.dnf.reverse1;

import com.dnf.reverse1.model.Query;

public class QueryMan {

	public static void main(String[] args) {
		IndexCreate indexMode = new IndexCreate();
		
		Query query = new Query();
		query.setpId("1001");
		query.setCountry("us");
		query.setApps("3,4");
		query.setLanguage("es");
		query.setVer("a_2");

		
		for (int i = 0; i < 40; i++) {
			long n = System.currentTimeMillis();
			indexMode.queryIndex(query);
			System.out.println(System.currentTimeMillis() - n);
		}
	}
}
