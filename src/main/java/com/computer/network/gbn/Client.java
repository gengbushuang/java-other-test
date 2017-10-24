package com.computer.network.gbn;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
			new ReceiverThread().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int base = 1;

	private int nextseqnum = 1;

	private int N = 4;

	private List<Packet> lists = new ArrayList<Packet>();

	//gbn协议
	//N表示滑动窗口大小[base,nextseqnum],也想当于缓存多少个发送分组
	//每发送一个分组就nextseqnum+1
	//每接收到一个ack分组就base=返回当前nextseqnum序号+1
	
	//当调用send()的时候,先检测窗口是否满了,是否有N个发送了还没有收到ACK分组.
	//如果窗口没有满,就产生一个发送分组并将其发送.
	//收到一个ACK,对序列号为N的分组采取累计确认,包含N在内.
	//超时的时候(目前还没有写)，如果有超时发送方重传所有发送还没有确认的分组.[base,nextseqnum-1]
	
	public void send(String data) throws IOException, ClassNotFoundException {
		if (!sender.rdt_send(data)) {
			return;
		}
		if (nextseqnum < base + N) {
			int checksum = sender.check_sum(data);
			Packet sndpkt = sender.make_pkt(nextseqnum, data, checksum);
			lists.add(sndpkt);
			System.out.println("客户端发送:");
			System.out.println(sndpkt);
			sender.udt_send(sndpkt, getOstream());
			if (base == nextseqnum) {
				System.out.println("start_timer-->nextseqnum==" + nextseqnum);
			}
			nextseqnum++;
		} else {
			refuse_data(data);
		}
	}

	private void refuse_data(String data) {
		System.out.println("窗口满了等下在发送!");
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

	class ReceiverThread extends Thread {

		public ReceiverThread(){
			setDaemon(true);
		}
		
		@Override
		public void run() {
			ObjectInputStream ois = null;
			Packet rcvpkt = null;
			while (!socket.isClosed() && !Thread.currentThread().isInterrupted()) {
				System.out.println(socket.isClosed());
				try {
					ois = new ObjectInputStream(istream);
					rcvpkt = (Packet) ois.readObject();
					System.out.println("客户端接收:");
					System.out.println(rcvpkt);
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				if (receiver.rdt_rcv(rcvpkt) && receiver.notcorrupt(rcvpkt)) {
					base = receiver.getacknum(rcvpkt) + 1;
					if (base == nextseqnum) {
						//超时
						System.out.println("stop_timer-->base==" + base);
					} else {
						//超时
						System.out.println("start_timer-->base==" + base);
					}
				} else {
					try {
						System.out.println("客户端重传:");
						System.out.println(lists.get(base));
						sender.udt_send(lists.get(base), getOstream());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public static void main(String[] args) throws URISyntaxException, ClassNotFoundException, IOException {
		//有bug没有解决，close的时候没有确认数据有没有全部接收完成。
		URI u = new URI("http://localhost:12345");
		Client client = new Client(u);
		client.run();
		client.send("message-1");
		client.send("message-2");
		client.send("message-3");
		
		client.send("message-4");
		client.send("message-5");
		client.send("message-7");
		client.send("message-8");
		client.send("message-9");
		client.send("message-10");

		client.close();
	}
}
