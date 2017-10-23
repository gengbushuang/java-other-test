package com.computer.network.rdt2_2;

public class Receiver {

	private final static String ACK = "ACK";

	private int check_sum(String data) {
		return data.chars().reduce((x, y) -> x + y).getAsInt();
	}

	public boolean rdt_rcv(Packet rcvpkt) {
		return rcvpkt == null;
	}

	public boolean corrupt(Packet rcvpkt) {
		return (check_sum(rcvpkt.getData()) != rcvpkt.getChecksum());
	}

	public boolean notcorrupt(Packet rcvpkt) {
		return (check_sum(rcvpkt.getData()) == rcvpkt.getChecksum());
	}

	public boolean isACK(Packet rcvpkt, int seq) {
		return rcvpkt.equals(ACK) && rcvpkt.getSeq() == seq;
	}

	public boolean has_seq(Packet rcvpkt, int seq) {
		return rcvpkt.getSeq() == seq;
	}
}
