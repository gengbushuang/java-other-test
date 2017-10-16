package com.computer.network.rdt2;

import java.io.Serializable;

public class Packet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8114657516655312685L;

	private final String data;
	private final int checksum;
	private final byte mark;

	public Packet(String _data, int _checksum, byte _mark) {
		this.data = _data;
		this.checksum = _checksum;
		this.mark = _mark;
	}

	public String getData() {
		return data;
	}

	public int getChecksum() {
		return checksum;
	}

	public byte getMark() {
		return mark;
	}

	@Override
	public String toString() {
		return "Packet [data=" + data + ", checksum=" + checksum + ", mark=" + mark + "]";
	}

}
