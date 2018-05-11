package com.dnf.test;

import java.util.Random;

public class Country {
	
	static final String AD_COUNTRY = "test:ad:country:";
	
	String[] str = { "all", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AT", "AU", "AZ", "BB", "BD", "BE",
			"BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BR", "BS", "BW", "BY", "BZ", "CA", "CF", "CG", "CH",
			"CK", "CL", "CM", "CN", "CO", "CR", "CS", "CU", "CY", "CZ", "DE", "DJ", "DK", "DO", "DZ", "EC", "EE", "EG",
			"ES", "ET", "FI", "FJ", "FR", "GA", "GB", "GD", "GE", "GF", "GH", "GI", "GM", "GN", "GR", "GT", "GU", "GY",
			"HK", "HN", "HT", "HU", "ID", "IE", "IL", "IN", "IQ", "IR", "IS", "IT", "JM", "JO", "JP", "KE", "KG", "KH",
			"KP", "KR", "KT", "KW", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC",
			"MD", "MG", "ML", "MM", "MN", "MO", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NE", "NG", "NI",
			"NL", "NO", "NP", "NR", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PR", "PT", "PY", "QA", "RO",
			"RU", "SA", "SB", "SC", "SD", "SE", "SG", "SI", "SK", "SL", "SM", "SN", "SO", "SR", "ST", "SV", "SY", "SZ",
			"TD", "TG", "TH", "TJ", "TM", "TN", "TO", "TR", "TT", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VC", "VE",
			"VN", "YE", "YU", "ZA", "ZM", "ZR", "ZW" };
	
	Random random = new Random();

	public String getValue() {
		int nextInt = random.nextInt(str.length);
		return AD_COUNTRY+str[nextInt];
	}
	
	public String getV() {
		int nextInt = random.nextInt(str.length);
		return str[nextInt];
	}
}
