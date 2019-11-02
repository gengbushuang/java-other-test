package com.reactor.two;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Handler implements Runnable {

	final SocketChannel socket;
	final SelectionKey sk;

	static final int READING = 0, SENDING = 1;
	int state = READING;

	ByteBuffer input = ByteBuffer.allocate(50);
	ByteBuffer output = ByteBuffer.allocate(50);

	final private Charset charset = Charset.forName("UTF-8");
	ByteBuffer limiter = ByteBuffer.wrap("\n".getBytes());

	public Handler(SocketChannel accept, Selector selector) throws IOException {
		socket = accept;
		socket.configureBlocking(false);
		sk = socket.register(selector, 0);
		sk.attach(this);
		sk.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
		System.out.println(socket.getLocalAddress() + "===" + socket.getRemoteAddress());
	}

	@Override
	public void run() {
		try {
			socket.read(input);
			if (inputIsComplete()) {
				process();
				sk.attach(new Sender());
				sk.interestOps(SelectionKey.OP_WRITE);
	            sk.selector().wakeup();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process() {
		input.flip();
		docode(input);
		input.clear();
	}

	private boolean inputIsComplete() {
		int limit = input.limit();
		int position = input.position();
		System.out.println("limit--" + limit + "--position" + position);
		if (!(limit > position)) {
			return true;
		}
		return false;
	}

	private void docode(ByteBuffer ioByte) {
		int oldPos = ioByte.position();
		int oldLimit = ioByte.limit();
		while (ioByte.hasRemaining()) {
			byte b = ioByte.get();
			if (limiter.get(matchCount) == b) {
				matchCount++;
				if (matchCount == limiter.limit()) {
					int pos = ioByte.position();
					ioByte.limit(pos);
					ioByte.position(oldPos);

					tmp.put(ioByte);
					ioByte.limit(oldLimit);
					ioByte.position(pos);

					tmp.flip();
					tmp.limit(tmp.limit() - 1);
					String value = charset.decode(tmp).toString();
					System.out.println("----" + value + "----");
					tmp.clear();
					output.put("ok\n".getBytes());
					oldPos = pos;
					matchCount = 0;
				}
			} else {
				ioByte.position(Math.max(0, ioByte.position() - matchCount));
			}
		}
		ioByte.position(oldPos);
		tmp.put(ioByte);
	}

	int matchCount = 0;
	ByteBuffer tmp = ByteBuffer.allocate(2048);

	class Sender implements Runnable {
		public void run() {
			try {
				output.flip();
				socket.write(output);
				output.clear();
//				sk.cancel();
//				sk.selector().wakeup();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		System.out.println(Math.max(0, 3));
		ByteBuffer tmpdd = ByteBuffer.allocate(2048);
		System.out.println(tmpdd.remaining());

	}

}
