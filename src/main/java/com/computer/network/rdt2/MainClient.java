package com.computer.network.rdt2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainClient {

	public static void main(String[] args) throws ClassNotFoundException, IOException, URISyntaxException {
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
