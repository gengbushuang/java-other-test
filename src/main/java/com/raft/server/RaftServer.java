package com.raft.server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;
import com.raft.server.config.ClusterConfiguration;

public class RaftServer {
	
	private int id;
	private int votesResponded;
    private int votesGranted;
    private int leader;
    private boolean electionCompleted;
    private AtomicInteger snapshotInProgress;
    private Random random;

	private ServerRole role;

	private ServerState state;

	private ClusterConfiguration config;
	
	private Object StateMachine;
	
	//选举超时任务
	private Callable<Void> electionTimeoutTask;

	public RaftServer() {
		this.id = serverId();
		this.votesGranted = 0;
        this.votesResponded = 0;
        this.leader = -1;
        this.electionCompleted = false;
        this.snapshotInProgress = new AtomicInteger(0);
        this.random = new Random(Calendar.getInstance().getTimeInMillis());
		///
		StateMachine = new Object();

		this.config = initConfig();

		role = ServerRole.Follower;

		if (this.state == null) {
			this.state = new ServerState();
			this.state.setTerm(0);
			this.state.setVotedFor(-1);
			this.state.setCommitIndex(0);
		}
	}

	
	private int serverId() {
		Properties props = new Properties();
		String serverIdValue = props.getProperty("server.id");
		return serverIdValue == null || serverIdValue.length() == 0 ? -1 : Integer.parseInt(serverIdValue.trim());
	}
	
	private ClusterConfiguration initConfig() {
		String dataDirectory = "/Users/gbs/tool/git/java-other-test/src/main/java/com/raft/server/config/";
		Path path = Paths.get(dataDirectory);

		try {
			String json = Files.toString(new File(path.resolve("init-cluster.json").toString()), Charset.forName("utf-8"));
			return JSON.parseObject(json, ClusterConfiguration.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("failed to read in cluster config", e);
		}
	}

}
