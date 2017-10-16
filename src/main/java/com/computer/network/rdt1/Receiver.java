package com.computer.network.rdt1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

/**
 * rtd1.0 完全可靠信道的可靠数据传输 接收数据方
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年10月12日 下午2:39:09
 */
public class Receiver implements Runnable {

	private final InetSocketAddress address;

	public Receiver(int port) {
		this(new InetSocketAddress(port));
	}

	public Receiver(InetSocketAddress address) {
		this.address = address;
		setTcpNoDelay(false);
	}

	// rdt通过rdt_rcv(packet)事件从底层信道接受一个分组，
	// 从分组中取出数据(经由extract(packet,data)动作)，
	// 并将数据上传给较高层(通过deliver_data(data)动作)
	public void rtd_rcv(Packet packet) {
		// extract(packet,data)
		String data = extract(packet);
		deliver_data(data);
	}

	public String extract(Packet packet) {
		return null;
	}

	public void deliver_data(String data) {

	}

	private ServerSocketChannel server;

	private Selector selector;

	private Thread selectorthread;

	private boolean tcpNoDelay;

	private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

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
						byte[] b;
						int position = 0;
						int limit = 0;
						int count = 0;
						while (bytesRead > 0) {
							buffer.flip();
							position = buffer.position();
							System.out.println("1 position -->" + position);
							while (buffer.hasRemaining()) {
								byte c = buffer.get();
								if (c == '\n') {
									limit = buffer.position();
									count = limit - position;
									System.out.println("position-->" + position + "(" + count + ")limit->" + limit);
									position = limit;
								}
							}
							System.out.println("2 position -->" + buffer.position());
							if (position == buffer.position()) {
								buffer.clear();
							} else {
								buffer.position(position);
							}

							// if(limit < buffer.position()-1){
							// buffer.position(position);
							// }else{
							// buffer.clear();
							// limit=0;
							// count=0;
							// }
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
