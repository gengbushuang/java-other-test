package com.dnf.reverse1;

public interface DelBuilder {
	
	public void del(String key, String... member);
	
	public void delEliminate(String field, String value, int id);
}
