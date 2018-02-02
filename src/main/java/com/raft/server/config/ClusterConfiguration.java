package com.raft.server.config;

import java.util.LinkedList;
import java.util.List;

public class ClusterConfiguration {
	private long logIndex;
	private long lastLogIndex;
	private List<ClusterServer> servers;

	public ClusterConfiguration() {
		this.servers = new LinkedList<ClusterServer>();
		this.logIndex = 0;
		this.lastLogIndex = 0;
	}

	public long getLogIndex() {
		return logIndex;
	}

	public void setLogIndex(long logIndex) {
		this.logIndex = logIndex;
	}

	public long getLastLogIndex() {
		return lastLogIndex;
	}

	public void setLastLogIndex(long lastLogIndex) {
		this.lastLogIndex = lastLogIndex;
	}

	public List<ClusterServer> getServers() {
		return servers;
	}

	public void setServers(List<ClusterServer> servers) {
		this.servers = servers;
	}
	
	public ClusterServer getServer(int id) {
		for (ClusterServer server : this.servers) {
			if (server.getId() == id) {
				return server;
			}
		}
		return null;
	}
	
}
