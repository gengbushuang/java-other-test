package com.reactor.first;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {

	final Selector selector;
	final ServerSocketChannel serverSocket;

	public Reactor(int port) throws IOException {
		this.selector = Selector.open();
		this.serverSocket = ServerSocketChannel.open();
		this.serverSocket.bind(new InetSocketAddress(port));
		this.serverSocket.configureBlocking(false);
		this.serverSocket.register(this.selector, SelectionKey.OP_ACCEPT, new Acceptor());
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				selector.select();
				Set<SelectionKey> keys = selector.keys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					dispatch((SelectionKey)iterator.next());
				}
				keys.clear();
			}
		} catch (IOException e) {
		}

	}

	private void dispatch(SelectionKey k) {
		Runnable r = (Runnable)k.attachment();
		if(r!=null){
			r.run();
		}
	}

	class Acceptor implements Runnable {

		@Override
		public void run() {
			try{
				SocketChannel c  = serverSocket.accept();
				if(c!=null){
					new Handler(selector, c);
				}
			} catch (IOException e) {
			}
		}

	}
	public static void main(String [] args){
		System.out.println(SelectionKey.OP_ACCEPT);
		System.out.println(SelectionKey.OP_CONNECT);
		System.out.println(SelectionKey.OP_READ);
		System.out.println(SelectionKey.OP_WRITE);
	}
}
