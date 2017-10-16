package com.computer.network.rdt2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Receiver implements Runnable {

	private final static int NAK = 0;
	private final static int ACK = 1;

	// 检验码，先有java自带的hash代替
	private int check_sum(String data) {
		return data.hashCode();
	}

	public void udt_send(Packet sndpkt) {

	}

	public String extract(Packet packet) {
		return null;
	}

	public void deliver_data(String data) {

	}

	public Packet make_pkt(String data,int mark) {
		return new Packet(data, 0, (byte) mark);
	}

	// rdt2.0接收方只有一个状态
	public void rdt_rcv(Packet rcvpkt) {
		System.out.println("服务器端接收:");
		show(rcvpkt);
		Packet sndpkt;
		if (rcvpkt != null && notcorrupt(rcvpkt)) {
			// extract(packet,data)
			String data = extract(rcvpkt);
			deliver_data(data);
			sndpkt = make_pkt("ACK",ACK);
		} else {
			sndpkt = make_pkt("NAK",NAK);
		}
		System.out.println("服务器端发送:");
		show(sndpkt);
		udt_send(sndpkt);
	}

	public boolean corrupt(Packet rcvpkt) {
		return rcvpkt.getChecksum() != check_sum(rcvpkt.getData());
	}

	public boolean notcorrupt(Packet rcvpkt) {
		return rcvpkt.getChecksum() == check_sum(rcvpkt.getData());
	}

	public void show(Packet rcvpkt) {
		System.out.println(rcvpkt);
	}

	private final InetSocketAddress address;

	private ServerSocketChannel server;

	private Selector selector;

	private Thread selectorthread;

	private boolean tcpNoDelay;

	private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

	public Receiver(int port) {
		this(new InetSocketAddress(port));
	}

	public Receiver(InetSocketAddress address) {
		this.address = address;
		setTcpNoDelay(false);
	}

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	@Override
	public void run() {

		selectorthread = Thread.currentThread();

		try {
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			ServerSocket socket = server.socket();
			socket.bind(address);
			selector = Selector.open();
			server.register(selector, server.validOps());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (!selectorthread.isInterrupted()) {
			SelectionKey key = null;
			try {
				int n = selector.select();
				System.out.println("selector n-->" + n);
				if (n == 0) {
					continue;
				}
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> i = keys.iterator();

				while (i.hasNext()) {
					key = i.next();
					i.remove();
					System.out.println("key-->" + key);
					//
					if (key.isAcceptable()) {
						System.out.println("Acceptable-->start");
						SocketChannel channel = server.accept();
						if (channel == null) {
							continue;
						}
						channel.configureBlocking(false);
						Socket socket = channel.socket();
						socket.setTcpNoDelay(isTcpNoDelay());
						socket.setKeepAlive(true);
						channel.register(selector, SelectionKey.OP_READ);
						// selector.wakeup();
						System.out.println("Acceptable-->end");
						continue;
					}

					if (key.isReadable()) {
						System.out.println("Readable-->start");
						SocketChannel sc = (SocketChannel) key.channel();
						buffer.clear();
						long bytesRead = sc.read(buffer);
						while (bytesRead > 0) {
							buffer.flip();
							while (buffer.hasRemaining()) {
								System.out.print((char) buffer.get());
							}
							System.out.println();
							buffer.clear();
							bytesRead = sc.read(buffer);
						}
						if (bytesRead < 0) {
							sc.close();
						}
						System.out.println("Readable-->end");
						continue;
					}

					if (key.isWritable()) {
						System.out.println("Writable-->");
						continue;
					}
				}
				keys.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
