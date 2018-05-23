package com.dnf.test.model;

/**
 * 
 * @author gengbushuang
 *
 *         term: state in not { CA }
 *
 *         Term{id: xxx, key: state, val: CA, belong: false}
 */
public class Term {

	// term索引ID
	private int id;

	private String key;

	private String value;

	public Term(String key, String value) {
		this(0, key, value);
	}

	public Term(int id, String key, String value) {
		this.key = key;
		this.value = value;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
