package com.computer.network.rdt2_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class Client implements Runnable {

	protected URI uri = null;

	private Sender sender = new Sender();

	private Receiver receiver = new Receiver();

	private Socket socket = null;

	private InputStream istream;

	private OutputStream ostream;

	private Proxy proxy = Proxy.NO_PROXY;

	private int connectTimeout = 0;

	private boolean tcpNoDelay;

	public Client(URI serverUri) {
		this(serverUri, 0);
	}

	public Client(URI serverUri, int connectTimeout) {
		this.uri = serverUri;
		this.connectTimeout = connectTimeout;
		setTcpNoDelay(false);
	}

	public Proxy getProxy() {
		return proxy;
	}

	private int getPort() {
		int port = uri.getPort();
		return port;
	}

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	public InputStream getIstream() {
		return istream;
	}

	public OutputStream getOstream() {
		return ostream;
	}

	@Override
	public void run() {
		try {
			if (socket == null) {
				socket = new Socket(proxy);
			} else if (socket.isClosed()) {
				throw new IOException();
			}
			socket.setTcpNoDelay(isTcpNoDelay());
			if (!socket.isBound()) {
				socket.connect(new InetSocketAddress(uri.getHost(), getPort()), connectTimeout);
			}
			istream = socket.getInputStream();
			ostream = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int seq = 0;

	public void send(String data) throws IOException, ClassNotFoundException {
		if (!sender.rdt_send(data)) {
			return;
		}
		int checksum = sender.check_sum(data);
		Packet sndpkt = sender.make_pkt(seq, data, checksum, (byte) 0);
		System.out.println("客户端发送:");
		System.out.println(sndpkt);
		Packet rcvpkt = null;
		do {
			sender.udt_send(sndpkt, getOstream());
			ObjectInputStream ois = new ObjectInputStream(istream);
			rcvpkt = (Packet) ois.readObject();
			System.out.println("客户端接收:");
			System.out.println(rcvpkt);
		} while (receiver.rdt_rcv(rcvpkt) && (receiver.corrupt(rcvpkt) || receiver.isNAK(rcvpkt)));

		if (receiver.rdt_rcv(rcvpkt) && !receiver.notcorrupt(rcvpkt) && !receiver.isACK(rcvpkt)) {
			throw new IOException("ACK is ERROR!" + rcvpkt);
		}
		seq++;
	}

	public void close() throws IOException {
		if (ostream != null) {
			ostream.close();
		}
		if (istream != null) {
			istream.close();
		}
		if (socket != null) {
			socket.close();
		}
	}

	public static void main(String[] args) throws URISyntaxException, ClassNotFoundException, IOException {
		URI u = new URI("http://localhost:12345");
		Client client = new Client(u);
		client.run();
		client.send("message-1");
		client.send("message-2");
		client.send("message-3");

		client.close();
	}
}
