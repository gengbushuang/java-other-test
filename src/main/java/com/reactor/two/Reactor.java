package com.reactor.two;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Reactor implements Runnable {

	final Selector selector;
	final ServerSocketChannel chanel;

	Reactor(int port) throws IOException {
		selector = Selector.open();
		chanel = ServerSocketChannel.open();
		chanel.socket().bind(new InetSocketAddress(port));
		chanel.configureBlocking(false);
		SelectionKey sk = chanel.register(selector, SelectionKey.OP_ACCEPT);
		sk.attach(new Acceptor());
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				int select = selector.select();
				System.out.println("select--->"+select);
				Iterator<SelectionKey> sks = selector.selectedKeys().iterator();
				while (sks.hasNext()) {
					SelectionKey key = sks.next();
					System.out.println(key);
					sks.remove();
					dispatch(key);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void dispatch(SelectionKey skey) {
		Runnable r = (Runnable) skey.attachment();
		if (r != null) {
			r.run();
		}
	};

	class Acceptor implements Runnable {

		@Override
		public void run() {
			try {
				SocketChannel accept = chanel.accept();
				if (accept != null) {
					new Handler(accept, selector);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		try {
			new Reactor(12345).run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
