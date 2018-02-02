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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;
import com.raft.server.config.ClusterConfiguration;
import com.raft.server.config.ClusterServer;
import com.raft.server.config.RaftParameters;

public class RaftServer {

	private ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());

	RaftParameters raftParameters = new RaftParameters().withElectionTimeoutUpper(5000)
			// 选举最高超时时间
			.withElectionTimeoutLower(3000)
			// 选举最低超时时间
			.withHeartbeatInterval(1500).withRpcFailureBackoff(500).withMaximumAppendingSize(200).withLogSyncBatchSize(5).withLogSyncStoppingGap(5).withSnapshotEnabled(5000)
			.withSyncSnapshotBlockSize(0);

	private ScheduledFuture<?> scheduledElection;

	private int id;
	private int votesResponded;
	private int votesGranted;
	private int leader;

	private AtomicInteger snapshotInProgress;
	private Random random;

	private ServerRole role;

	private ServerState state;

	private ClusterConfiguration config;

	private Object StateMachine;

	// 选举
	private boolean electionCompleted;
	// 选举超时任务
	private Callable<Void> electionTimeoutTask;

	private boolean configChanging = false;
	private boolean catchingUp = false;
	private int steppingDown = 0;

	public RaftServer() {
		this.id = serverId();
		this.votesGranted = 0;
		this.votesResponded = 0;
		this.leader = -1;

		this.snapshotInProgress = new AtomicInteger(0);
		this.random = new Random(Calendar.getInstance().getTimeInMillis());
		// /
		StateMachine = new Object();

		this.config = initConfig();

		// 选举
		this.electionCompleted = false;
		this.electionTimeoutTask = new Callable<Void>() {
			@Override
			public Void call() throws Exception {

				return null;
			}
		};

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

	private synchronized void handleElectionTimeout() {

		if (this.steppingDown > 0) {
			if (--this.steppingDown == 0) {
				ClusterServer server = this.config.getServer(this.id);
				if (server != null) {

				}
				System.exit(0);
				return;
			}
			this.restartElectionTimer();
			return;
		}

		if (this.catchingUp) {
			this.restartElectionTimer();
			return;
		}

		if (this.role == ServerRole.Leader) {
			System.exit(-1);
			return;
		}
		// 任期号加1
		this.state.increaseTerm();
		this.state.setVotedFor(-1);
		// 转变为候选人
		this.role = ServerRole.Candidate;
		this.votesGranted = 0;
		this.votesResponded = 0;
		this.electionCompleted = false;
		this.requestVote();

	}

	private void restartElectionTimer() {
		if (this.catchingUp) {
			return;
		}
		// 不为空关闭该定时任务的时候
		if (this.scheduledElection != null) {
			this.scheduledElection.cancel(false);
		}
		RaftParameters parameters = this.raftParameters;
		// 最低超时时间<[最低的超时时间加上(最高超时时间减去最低超时时间获取中间随机数)]<最高的超时时间
		int electionTimeout = parameters.getElectionTimeoutLowerBound() + this.random.nextInt(parameters.getElectionTimeoutUpperBound() - parameters.getElectionTimeoutLowerBound() + 1);
		// 执行选举
		this.scheduledElection = scheduledExecutor.schedule(this.electionTimeoutTask, electionTimeout, TimeUnit.MILLISECONDS);
	}
	
	//
	private void requestVote() {

	}

}
