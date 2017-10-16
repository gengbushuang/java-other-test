package com.computer.network.rdt1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ManClient {
	public static void main(String[] args) throws URISyntaxException, IOException {
		URI u = new URI("http://localhost:12345");
		Sender sender = new Sender(u);
		
		sender.run();
		
		String msg = "message-1";
		sender.rtd_send(msg);
		sender.rtd_send("message-2");
		sender.rtd_send("message-3");
		
		sender.close();
	}
}
