package com.rpc.tcp_ip;

public class VoteMsg {

	private boolean isinquiry;
	private boolean isResponse;
	private int candidateID;
	private long voteCount;
	
	public static final int MAX_CANDIDATE_ID=1000;
	
	public VoteMsg(boolean isResponse,boolean isInquiry,int candidateID,long voteCount){
		if(voteCount !=0&&!isResponse){
			throw new IllegalArgumentException("a");
		}
		if(candidateID<0 || candidateID>MAX_CANDIDATE_ID){
			throw new IllegalArgumentException("b");
		}
		if(voteCount<0){
			throw new IllegalArgumentException("c");
		}
		this.candidateID = candidateID;
		this.isResponse = isResponse;
		this.isinquiry = isInquiry;
		this.voteCount = voteCount;
	}

	public boolean isIsinquiry() {
		return isinquiry;
	}

	public void setIsinquiry(boolean isinquiry) {
		this.isinquiry = isinquiry;
	}

	public boolean isResponse() {
		return isResponse;
	}

	public void setResponse(boolean isResponse) {
		this.isResponse = isResponse;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidateID) {
		if(candidateID<0 || candidateID>MAX_CANDIDATE_ID){
			throw new IllegalArgumentException("b");
		}
		this.candidateID = candidateID;
	}

	public long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(long voteCount) {
		if(voteCount !=0&&!isResponse){
			throw new IllegalArgumentException("a");
		}
		this.voteCount = voteCount;
	}
	
	
}
