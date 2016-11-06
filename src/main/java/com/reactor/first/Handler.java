package com.reactor.first;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {
	final SocketChannel socket;
	final SelectionKey sk;

	ByteBuffer input = ByteBuffer.allocate(1024);
	ByteBuffer output = ByteBuffer.allocate(1024);
	
	public Handler(Selector sel, SocketChannel c) throws IOException {
		socket = c;
		c.configureBlocking(false);
		sk = socket.register(sel, 0);
		sk.attach(this);
		sk.interestOps(SelectionKey.OP_READ);
		sel.wakeup();
	}

	@Override
	public void run() {
		try {
			socket.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sk.attach(new Sender());
		sk.interestOps(SelectionKey.OP_WRITE);
		sk.selector().wakeup();
	}
	
	class Sender implements Runnable {
		@Override
		public void run() {
			try {
				socket.write(output);
				sk.cancel();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
