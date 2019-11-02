package com.reactor.three;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Connection {

	private SocketChannel channel;

	ByteBuffer input = ByteBuffer.allocate(50);
	ByteBuffer output = ByteBuffer.allocate(50);

	public Connection(SelectionKey readKey, SocketChannel channel, long currentTimeMillis) {
		this.channel = channel;
	}

	public void readAndProcess() throws IOException {
		channel.read(input);
		if (inputIsComplete()) {
			process();
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
	
	final private Charset charset = Charset.forName("UTF-8");
	ByteBuffer limiter = ByteBuffer.wrap("\n".getBytes());
	int matchCount = 0;
	ByteBuffer tmp = ByteBuffer.allocate(2048);
	
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
					output.flip();
					try {
						channel.write(output);
					} catch (IOException e) {
						e.printStackTrace();
					}
					output.clear();
					
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
}
