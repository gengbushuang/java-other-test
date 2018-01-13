package com.computer.network.rdt2_1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class Service implements Runnable {

	private final static int NAK = 0;
	private final static int ACK = 1;

	private final InetSocketAddress address;

	private ServerSocketChannel server;

	private Sender sender = new Sender();

	private Receiver receiver = new Receiver();

	private Selector selector;

	private Thread selectorthread;

	private boolean tcpNoDelay;

	private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

	private ByteBuffer buf = ByteBuffer.allocateDirect(1024);

	public Service(int port) {
		this(new InetSocketAddress(port));
	}

	public Service(InetSocketAddress address) {
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
		SelectionKey key = null;
		while (!selectorthread.isInterrupted()) {
			try {
				int n = selector.select();
				if (n == 0) {
					continue;
				}
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> i = keys.iterator();
				while (i.hasNext()) {
					key = i.next();
					i.remove();
					if (key.isAcceptable()) {
						handleAccept(key, selector);
					} else if (key.isReadable()) {
						handleRead(key, selector);
					} else if (key.isWritable()) {
						handleWrit(key, selector);
					}

				}
				keys.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleWrit(SelectionKey key, Selector selector2) {
		// TODO Auto-generated method stub

	}

	private void handleRead(SelectionKey key, Selector selector2) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();
		byte[] bytes;
		ObjectInputStream ois;
		ObjectOutputStream oos;
		buffer.clear();
		long bytesRead = sc.read(buffer);
		while (bytesRead > 0) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				byte c = buffer.get();
				if (c == '\n') {
					buf.flip();
					int limit = buf.limit();
					bytes = new byte[limit];
					buf.get(bytes);
					ois = new ObjectInputStream(new ByteInputStream(bytes, bytes.length));
					try {
						Packet rcvpkt = (Packet) ois.readObject();
						System.out.println("服务器端接收:");
						System.out.println(rcvpkt);
						Packet sndpkt = null;
						if (!receiver.rdt_rcv(rcvpkt) && receiver.notcorrupt(rcvpkt) && receiver.has_seq(rcvpkt)) {
							int checksum = sender.check_sum("ACK");
							sndpkt = sender.make_pkt(0, "ACK", checksum, (byte) ACK);
						} else if (receiver.rdt_rcv(rcvpkt) && receiver.corrupt(rcvpkt)) {
							int checksum = sender.check_sum("NAK");
							sndpkt = sender.make_pkt(0, "NAK", checksum, (byte) NAK);
						}
						System.out.println("服务器端发送:");
						System.out.println(sndpkt);
						ByteArrayOutputStream bao = new ByteArrayOutputStream();
						oos = new ObjectOutputStream(bao);
						oos.writeObject(sndpkt);
						ByteBuffer wrap = ByteBuffer.wrap(bao.toByteArray());
						sc.write(wrap);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					buf.clear();
				} else {
					buf.put(c);
				}
			}
			buffer.clear();
			bytesRead = sc.read(buffer);
		}
		if (bytesRead < 0) {
			sc.close();
		}
	}

	private void handleAccept(SelectionKey key, Selector selector2) throws IOException {
		SocketChannel channel = server.accept();
		if (channel == null) {
			return;
		}
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		socket.setTcpNoDelay(isTcpNoDelay());
		socket.setKeepAlive(true);
		channel.register(selector2, SelectionKey.OP_READ);
	}

	public static void main(String[] args) {
		Service service = new Service(12345);
		service.run();
	}

}