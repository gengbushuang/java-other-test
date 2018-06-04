package com.dnf.reverse2.model;

public class Term extends ID {

	private String key;

	private String value;

	public Term(String key, String value) {
		this(0, key, value);
	}

	public Term(Integer id, String key, String value) {
		super(id);
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Term other = (Term) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(50);
		sb.append("{\"id\":").append(getId()).append(",\"key\":\"").append(key).append("\",\"value\":\"").append(value)
				.append("\"}");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Term term = new Term(2, "wo", "men");
		System.out.println(term);
	}
}
