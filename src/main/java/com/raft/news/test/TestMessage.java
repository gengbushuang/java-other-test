package com.raft.news.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.raft.news.message.RaftMessageHandler;
import com.raft.news.message.RaftMessageType;
import com.raft.news.message.RaftRequestMessage;
import com.raft.news.message.RaftResponseMessage;

public class TestMessage implements RaftMessageHandler {

	private Logger logger;

	public TestMessage() {
		this.logger = LogManager.getLogger(getClass());
	}

	@Override
	public RaftResponseMessage processRequest(RaftRequestMessage request) {
		this.logger.debug("Receive a %s message from %d with LastLogIndex=%d, LastLogTerm=%d, EntriesLength=%d, CommitIndex=%d and Term=%d", request.getMessageType().toString(), request.getSource(),
				request.getLastLogIndex(), request.getLastLogTerm(), request.getLogEntries() == null ? 0 : request.getLogEntries().length, request.getCommitIndex(), request.getTerm());
		RaftResponseMessage response = null;
		if (request.getMessageType() == RaftMessageType.AppendEntriesRequest) {
			response = this.handleAppendEntriesRequest(request);
		} else if (request.getMessageType() == RaftMessageType.RequestVoteRequest) {
			response = this.handleVoteRequest(request);
		} else if (request.getMessageType() == RaftMessageType.ClientRequest) {
			response = this.handleClientRequest(request);
		} else {
			response = this.handleExtendedMessages(request);
		}
		if (response != null) {
			this.logger.debug("Response back a %s message to %d with Accepted=%s, Term=%d, NextIndex=%d", response.getMessageType().toString(), response.getDestination(),
					String.valueOf(response.isAccepted()), response.getTerm(), response.getNextIndex());
		}

		return response;
	}

	private synchronized RaftResponseMessage handleVoteRequest(RaftRequestMessage request) {

	}

}
