package com.raft.news.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import com.raft.news.message.RaftRequestMessage;
import com.raft.news.message.RaftResponseMessage;

public class RpcTcpClient {
	private AsynchronousSocketChannel connection;
	private AsynchronousChannelGroup channelGroup;
	//
	private AtomicInteger readers;
	private AtomicInteger writers;
	// 地址
	private InetSocketAddress remote;

	public RpcTcpClient(InetSocketAddress remote, ExecutorService executorService) {
		this.remote = remote;

		this.readers = new AtomicInteger(0);
		this.writers = new AtomicInteger(0);
		try {
			this.channelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
		} catch (IOException err) {

		}
	}

	public synchronized CompletableFuture<RaftResponseMessage> send(final RaftRequestMessage request) {
		CompletableFuture<RaftResponseMessage> result = new CompletableFuture<RaftResponseMessage>();
		if(this.connection == null || !this.connection.isOpen()){
			
		}
	}
}
