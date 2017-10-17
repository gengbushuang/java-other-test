package com.computer.network.rdt2;

public class MainServlet {
	public static void main(String[] args) {
		Receiver receiver = new Receiver(12345);
		receiver.run();
	}
}
