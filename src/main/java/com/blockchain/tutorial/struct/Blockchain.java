package com.blockchain.tutorial.struct;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import com.dnf.Patr;

/**
 * 区块
 * 
 * @author gengbushuang
 *
 */
public class Blockchain {

	List<Block> blocks;

	public Blockchain() {
		blocks = new LinkedList<>();
		blocks.add(init());
	}

	private Block init() {
		Block block = new Block("Genesis Block".getBytes(), new byte[0]);
		ProofOfWork proofOfWork = new ProofOfWork(block);

		Patr<Integer, byte[]> run = proofOfWork.run();

		block.setNonce(run._1().intValue());
		block.setHash(run._2());

		return block;
	}

	public void addBlock(String data) {
		Block oldblock = blocks.get(blocks.size() - 1);
		Block newblock = new Block(data.getBytes(), oldblock.getHash());
		ProofOfWork proofOfWork = new ProofOfWork(newblock);
		Patr<Integer, byte[]> run = proofOfWork.run();

		newblock.setNonce(run._1().intValue());
		newblock.setHash(run._2());

		blocks.add(newblock);
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public static void main(String[] args) {
		Blockchain blockchain = new Blockchain();

		blockchain.addBlock("Send 1 BTC to Ivan");
		blockchain.addBlock("Send 2 more BTC to Ivan");

		for (Block block : blockchain.getBlocks()) {
			System.out.printf("prev hash: %s \n", Hex.encodeHexString(block.getPrevBlockHash()));
			System.out.printf("data: %s \n", new String(block.getDate()));
			System.out.printf("hash: %s \n", Hex.encodeHexString(block.getHash()));
			ProofOfWork proofOfWork = new ProofOfWork(block);
			System.out.printf("pow: %s \n", proofOfWork.validate());
			System.out.println();
		}
	}
}
