package com.raft.news.message;

public interface RaftMessageHandler {

	public RaftResponseMessage processRequest(RaftRequestMessage requestMessage);
}
