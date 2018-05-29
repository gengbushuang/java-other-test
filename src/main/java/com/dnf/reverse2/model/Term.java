package com.dnf.reverse2.model;

import java.io.Serializable;

public class Term implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2228628048806616561L;

	private Integer id;

	private String key;

	private String value;

	public Term(Integer id, String key, String value) {
		this.id = id;
		this.key = key;
		this.value = value;
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
		return "Term [id=" + id + ", key=" + key + ", value=" + value + "]";
	}
}
