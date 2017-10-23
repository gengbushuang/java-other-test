package com.computer.network.rdt2_1;

import java.io.Serializable;

public class Packet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4802094238038247008L;

	private final String data;

	private final int seq;

	private final int checksum;

	private final byte mark;

	public Packet(int _seq, String _data, int _checksum, byte _mark) {
		this.seq = _seq;
		this.data = _data;
		this.checksum = _checksum;
		this.mark = _mark;
	}

	public String getData() {
		return data;
	}

	public int getSeq() {
		return seq;
	}

	public int getChecksum() {
		return checksum;
	}

	public byte getMark() {
		return mark;
	}

	@Override
	public String toString() {
		return "Packet [data=" + data + ", seq=" + seq + ", checksum=" + checksum + ", mark=" + mark + "]";
	}
}
