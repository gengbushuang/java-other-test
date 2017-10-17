package com.computer.network.rdt2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;

/**
 * rdt2.0协议
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年10月15日 下午8:57:53
 */
public class Sender implements Runnable {
	// 检验码，先有java自带的hash代替
	private int check_sum(String data) {
		return data.hashCode();
	}

	// rdt2.0的发送端有两个状态.
	// 当产生rdt_send(data)事件时，发送方将产生一个包含待发送数据的分组(sndpkt)，带有检验和
	// 然后经由udt_send(sndpkt)操作发送该分组。
	// 发送方协议等待来自接收方的ACK或NAK分组。
	// 如果收到一个ACK分组(rdt_rcv(rcvpkt) && isACK(rcvpkt)对应该事件)
	// 则发送方知道最近发送的分组已被正确接收，因此协议返回到等待来自上层数据状态。
	// 如果收到一个NAK分组，该协议重传最后分组并等待接收方为响应ACK或NAK
	// 当发送方处于等待ACK或NAK的状态时，不能从上层获取数据；就是说，rdt_send()事件不可能出现
	// 仅当接受到ACK并离开该状态时才能发生这样的事件。
	// 发送方将不会发送一块新数据，除非发送方确认接收方已正确接收当前分组。这种行为，rdt2.0的协议被称为停等协议。
	public void rtd_send(String data) throws IOException, ClassNotFoundException {
		int check_sum = check_sum(data);
		Packet sndpkt = make_pkt(data, check_sum);
		Packet rcvpkt = null;
		do {
			System.out.println("客户端发送:");
			show(sndpkt);
			udt_send(sndpkt);
			ObjectInputStream ois = new ObjectInputStream(istream);
			rcvpkt = (Packet) ois.readObject();
			System.out.println("客户端接收:");
			show(rcvpkt);
		} while (rdt_rcv(rcvpkt) && isNAK(rcvpkt));

		if (!rdt_rcv(rcvpkt) && !isACK(rcvpkt)) {
			throw new IOException("ACK is ERROR!" + rcvpkt);
		}
	}

	public Packet make_pkt(String data, int checksum) {
		return new Packet(data, checksum, (byte) 0);
	}

	public void udt_send(Packet packet) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(ostream);
		oos.writeObject(packet);
		oos.writeChar('\n');
		oos.flush();
	}

	public boolean rdt_rcv(Packet rcvpkt) {
		return rcvpkt != null;
	}

	public boolean isNAK(Packet rcvpkt) {
		return rcvpkt.getMark() == 0;
	}

	public boolean isACK(Packet rcvpkt) {
		return rcvpkt.getMark() == 1;
	}

	public void show(Packet rcvpkt) {
		System.out.println(rcvpkt);
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

	protected URI uri = null;

	private Socket socket = null;

	private InputStream istream;

	private OutputStream ostream;

	// private ObjectInputStream istream;
	//
	// private ObjectOutputStream ostream;

	private Proxy proxy = Proxy.NO_PROXY;

	private int connectTimeout = 0;

	private boolean tcpNoDelay;

	public Sender(URI serverUri) {
		this(serverUri, 0);
	}

	public Sender(URI serverUri, int connectTimeout) {
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
			// ostream = new ObjectOutputStream(socket.getOutputStream());
			// istream = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
