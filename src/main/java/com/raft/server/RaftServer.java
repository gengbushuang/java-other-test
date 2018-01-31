package com.raft.server;

public class RaftServer {

	private ServerRole role;

	private ServerState state;

	public RaftServer() {
		role = ServerRole.Follower;

		if (this.state == null) {
			this.state = new ServerState();
			this.state.setTerm(0);
			this.state.setVotedFor(-1);
			this.state.setCommitIndex(0);
		}
	}

}
