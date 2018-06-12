package com.blockchain.tutorial.struct;

import java.nio.ByteBuffer;

import com.blockchain.tutorial.util.BitSetConvert;
import com.blockchain.tutorial.util.Codec;
import com.dnf.Patr;
/**
 * 工作量证明
 * @author gengbushuang
 *
 */
public class ProofOfWork {
	int targetBits = 24;
	BitObject bitObject = new BitObject(256);

	Block block;

	public ProofOfWork(Block block) {
		bitObject.lsh(256 - targetBits);
		this.block = block;
	}

	public byte[] prepareData(int nonce) {
		ByteBuffer buffer = ByteBuffer
				.allocate(Long.BYTES * 3 + block.getDate().length + block.getPrevBlockHash().length);
		buffer.put(block.getPrevBlockHash());
		buffer.put(block.getDate());
		buffer.putLong(block.getTime());
		buffer.putLong(targetBits);
		buffer.putLong(nonce);
		return buffer.array();

	}
	
	public boolean validate() {
		byte[] data = prepareData(block.getNonce());
		byte[] hash = Codec.SHA256(data);
		int cmp = BitSetConvert.cmp(hash, bitObject.getBs());
		return cmp == -1;
	}

	public Patr<Integer, byte[]> run() {
		int nonce = 0;
		byte[] hash = null;
		while (nonce < Integer.MAX_VALUE) {
			byte[] data = prepareData(nonce);
			hash = Codec.SHA256(data);

			// System.out.println(Hex.encodeHexString(hash));
			// System.out.println(Hex.encodeHexString(bitObject.getBs()));
			//新块的hash要小于工作难度的hash
			int cmp = BitSetConvert.cmp(hash, bitObject.getBs());
			if (cmp == -1) {
				break;
			}
			nonce++;
		}
		return new Patr<Integer, byte[]>(nonce, hash);
	}

	public static void main(String[] args) {
		
	}
}
