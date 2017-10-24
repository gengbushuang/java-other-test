package com.computer.network.gbn;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Sender {

	public int check_sum(String data) {
		return data.chars().reduce((x, y) -> x + y).getAsInt();
	}

	public boolean rdt_send(String data) {
		return (data != null && !data.trim().equals(""));
	}

	public Packet make_pkt(int seq, String data, int checksum) {
		return new Packet(seq, data, checksum);
	}

	public void udt_send(Packet sndpkt, OutputStream ostream) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(ostream);
		oos.writeObject(sndpkt);
		oos.writeChar('\n');
		oos.flush();
	}

}
