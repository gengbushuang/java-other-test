package com.reactor.three;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MainReactor implements Runnable {

	private int port;
	private int thread;

	final Selector selector;
	final ServerSocketChannel chanel;
	SubReactor[] reactor = null;

	public MainReactor(int port) throws IOException {
		this(port, 2);
	}

	public MainReactor(int port, int thread) throws IOException {
		this.port = port;
		this.thread = thread;
		this.selector = Selector.open();
		chanel = ServerSocketChannel.open();
		chanel.socket().bind(new InetSocketAddress(port));
		chanel.configureBlocking(false);
		SelectionKey sk = chanel.register(this.selector, SelectionKey.OP_ACCEPT);
		sk.attach(new Listener());

		reactor = new SubReactor[thread];
		for (int i = 0; i < thread; i++) {
			reactor[i] = new SubReactor();
			reactor[i].start();
		}

	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				int select = selector.select();
				System.out.println("MainReactor:select--->"+select);
				Iterator<SelectionKey> sks = selector.selectedKeys().iterator();
				while (sks.hasNext()) {
					SelectionKey key = sks.next();
					Listener l = (Listener) key.attachment();
					if (l != null) {
						l.run();
					}
					sks.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Listener {

		private int next = 0;
		
		private final boolean tcpNoDelay = false;
		
		public synchronized void run() throws IOException {
			System.out.println("Listener:run-->"+next);
			SocketChannel accept = chanel.accept();
			if (accept != null) {
				accept.configureBlocking(false);
				accept.socket().setTcpNoDelay(tcpNoDelay);
				accept.socket().setKeepAlive(true);
				SelectionKey readKey = reactor[next].registerChannl(accept);
				Connection c = null;
				c = new Connection(readKey, accept, System.currentTimeMillis());
				readKey.attach(c);
//				reactor[next].startSelector();
//				if(next++==reactor.length){
//					next=0;
//				}
			}
			System.out.println("Listener:run-->end-->"+next);
		}
	}

	public static void main(String[] args) {
		try {
			new MainReactor(12345, 1).run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
