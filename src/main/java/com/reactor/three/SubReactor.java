package com.reactor.three;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SubReactor extends Thread {

	private final Selector readSelector;
	private boolean running = true;

	public SubReactor() throws IOException {
		this.readSelector = Selector.open();
	}

	public SelectionKey registerChannl(SocketChannel channel) throws ClosedChannelException {
		readSelector.wakeup();
		return channel.register(readSelector, SelectionKey.OP_READ);
	}

	@Override
	public void run() {
		while (running) {
			SelectionKey key = null;
			try {
				System.out.println("SubReactor:run");
				int select = readSelector.select();
				System.out.println("SubReactor:select--->"+select);
				Iterator<SelectionKey> iter = readSelector.selectedKeys().iterator();
				while (iter.hasNext()) {
					key = iter.next();
					iter.remove();
					if (key.isValid()) {
						if (key.isReadable()) {
							doRead(key);
						}
					}
					key = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void startSelector(){
		readSelector.wakeup();
	}
	
	private void doRead(SelectionKey key) {
		Connection c = (Connection) key.attachment();
		try {
			c.readAndProcess();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
