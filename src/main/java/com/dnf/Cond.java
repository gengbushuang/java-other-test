package com.dnf;

public class Cond {
	
	String key;
	String val;
	
	public Cond(String key,String val) {
		this.key = key;
		this.val = val;
	}

	public String getKey() {
		return key;
	}

	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "Cond [key=" + key + ", val=" + val + "]";
	}
}
