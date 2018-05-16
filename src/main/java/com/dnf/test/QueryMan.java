package com.dnf.test;

import com.dnf.reverse1.model.Query;

public class QueryMan {

	public static void main(String[] args) {
		IndexMode indexMode = new IndexMode();
		
		Query query = new Query();
		query.setpId("1001");
		query.setCountry("us");
		query.setApps("3,4");
		query.setLanguage("es");
		query.setVer("a_2");

		QueryCreate queryCreate = new QueryCreate(indexMode);

		for (int i = 0; i < 40; i++) {
			long n = System.currentTimeMillis();
			queryCreate.query(query);
			System.out.println(System.currentTimeMillis() - n);
		}
	}
}
