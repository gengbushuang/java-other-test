package com.computer.network.rdt1;

import java.io.Serializable;


public class Packet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1232272414015916363L;
	private String data;

	public Packet(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Packet [data=" + data + "]";
	}
	
}
