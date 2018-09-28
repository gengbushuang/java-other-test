package com.blockchain.tutorial.struct;

import java.nio.ByteBuffer;

import com.blockchain.tutorial.util.Codec;

public class Block {
	private long time;

	private byte[] date;
	
	private Transaction [] transactions;

	private byte[] prevBlockHash;

	private byte[] Hash;
	
	private int nonce;         

	public Block(byte[] date, byte[] prevBlockHash) {
		this.date = date;
		this.prevBlockHash = prevBlockHash;
		this.time = System.currentTimeMillis();
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + date.length + prevBlockHash.length);
		this.Hash = Codec.SHA256(buffer.array());
	}

	public long getTime() {
		return time;
	}

	public byte[] getDate() {
		return date;
	}

	public byte[] getPrevBlockHash() {
		return prevBlockHash;
	}

	public byte[] getHash() {
		return Hash;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public void setHash(byte[] hash) {
		Hash = hash;
	}

	public Transaction[] getTransactions() {
		return transactions;
	}
}
