package com.raft.news.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.raft.news.client.RpcTcpClient;
import com.raft.news.message.RaftMessageType;
import com.raft.news.message.RaftRequestMessage;

public class TestClientMain {
	public static void main(String[] args) throws IOException {
		int port = 12345;
		InetSocketAddress remote = new InetSocketAddress("127.0.0.1", port);
		int processors = Runtime.getRuntime().availableProcessors();
		RpcTcpClient tcpClient = new RpcTcpClient(remote, Executors.newFixedThreadPool(processors));
		
		RaftRequestMessage request = new RaftRequestMessage();
		request.setTerm(1);
		request.setSource(4);
		request.setDestination(2);
		request.setCommitIndex(3);
		request.setLastLogIndex(5);
		request.setMessageType(RaftMessageType.RequestVoteRequest);
		request.setLastLogTerm(7);
		
		tcpClient.send(request);
		System.in.read();
	}
}
