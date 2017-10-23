package com.computer.network.rdt2_1;

public class Receiver {

	private int seq = 0;
	
	private final static int NAK = 0;
	private final static int ACK = 1;

	private int check_sum(String data) {
		return data.chars().reduce((x, y) -> x + y).getAsInt();
	}

	public boolean rdt_rcv(Packet rcvpkt) {
		return rcvpkt == null;
	}

	public boolean corrupt(Packet rcvpkt) {
		return (check_sum(rcvpkt.getData()) != rcvpkt.getChecksum());
	}

	public boolean isNAK(Packet rcvpkt) {
		return rcvpkt.getMark() == NAK;
	}

	public boolean notcorrupt(Packet rcvpkt) {
		return (check_sum(rcvpkt.getData()) == rcvpkt.getChecksum());
	}

	public boolean isACK(Packet rcvpkt) {
		return rcvpkt.getMark() == ACK;
	}
	
	public boolean has_seq(Packet rcvpkt) {
		boolean n = (seq == rcvpkt.getSeq());
		if (n) {
			seq++;
		}
		return n;
	}
}
