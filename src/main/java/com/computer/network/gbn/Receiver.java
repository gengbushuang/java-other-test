package com.computer.network.gbn;

public class Receiver {

	private final static String ACK = "ACK";

	private int check_sum(String data) {
		return data.chars().reduce((x, y) -> x + y).getAsInt();
	}

	/**
	 * 判断是否为空
	 * @param rcvpkt
	 * @return true表示不为空 false表示为空
	 */
	public boolean rdt_rcv(Packet rcvpkt) {
		return rcvpkt != null;
	}
	
	/**
	 * 数据损坏
	 * @param rcvpkt
	 * @return true表示损坏
	 */
	public boolean corrupt(Packet rcvpkt) {
		return (check_sum(rcvpkt.getData()) != rcvpkt.getChecksum());
	}

	/**
	 * 数据没有损坏
	 * @param rcvpkt
	 * @return true表示没有损坏
	 */
	public boolean notcorrupt(Packet rcvpkt) {
		return (check_sum(rcvpkt.getData()) == rcvpkt.getChecksum());
	}

	public boolean isACK(Packet rcvpkt, int seq) {
		return rcvpkt.equals(ACK) && rcvpkt.getSeq() == seq;
	}

	public boolean has_seq(Packet rcvpkt, int seq) {
		return rcvpkt.getSeq() == seq;
	}
	
	public int getacknum(Packet rcvpkt){
		return rcvpkt.getSeq();
	}
}
