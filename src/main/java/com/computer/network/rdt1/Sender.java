package com.computer.network.rdt1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;

/**
 * rtd1.0 完全可靠信道的可靠数据传输 发送数据方
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年10月12日 下午12:19:38
 */
public class Sender implements Runnable {

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

	// 上面一层调用
	// rdt的发送端通过rtd_send(data)事件接受来自较高层的数据，
	// 产生一个包含该数据的分组(经由make_pkt(data)动作)，
	// 并将分组发送到信道中
	public void rtd_send(String data) {
		Packet packet = make_pkt(data);
		try {
			udt_send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Packet make_pkt(String data) {
		return new Packet(data);
	}

	public void udt_send(Packet packet) throws IOException {
		System.out.println("发送消息：");
		System.out.println(packet);
		ObjectOutputStream outputStream = new ObjectOutputStream(ostream);
		outputStream.writeObject(packet);
		outputStream.writeChar('\n');
		outputStream.flush();
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
			// istream = new ObjectInputStream(socket.getInputStream());
			// ostream = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
