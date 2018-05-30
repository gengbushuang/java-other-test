package com.dnf.reverse2;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import com.dnf.model.ConstantKey;
import com.dnf.reverse1.ReidsDb;
import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Pair;
import com.dnf.reverse2.model.Term;
import com.dnf.reverse2.struct.Dictionary;
import com.dnf.reverse2.util.BinaryUtils;

import redis.clients.jedis.Jedis;

public class Index implements Serializable {

	private String path = "D:/index/";
	
	public String assign_key = ConstantKey.BASEKEY + "assign:";
	
	public String conj_key = ConstantKey.BASEKEY + "conj:";
	
	public String term_key = ConstantKey.BASEKEY + "term:";

	Dictionary dictionary;

	public Index() throws IOException {
		dictionary = new Dictionary(path, "dict");
		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);
		
		loadConj(jedis);
		loadTerm(jedis);
		loadAssign(jedis);
		
		createIndex();
	}
	
	

	private void createIndex() {
		
	}



	private void loadAssign(Jedis jedis) {
		Set<byte[]> smembers = jedis.smembers(assign_key.getBytes());
		for(byte[] bs :smembers) {
			Assignment assignment = BinaryUtils.byteToAssign(bs);
			assigns.add(assignment);
			assignsHash.put(assignment, assignment.getId());
		}
	}

	private void loadTerm(Jedis jedis) {
		Set<byte[]> smembers = jedis.smembers(term_key.getBytes());
		for(byte[] bs :smembers) {
			Term term = BinaryUtils.byteToTerm(bs);
			terms.add(term);
		}
	}

	private void loadConj(Jedis jedis) {
		Set<byte[]> smembers = jedis.smembers(conj_key.getBytes());
		for(byte[] bs :smembers) {
			Conjunction conj = BinaryUtils.byteToConj(bs);
			conjs.add(conj);
			conjsHash.put(conj, conj.getId());
		}
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -2188635111237107354L;

//	public Map<String, Integer> termMap = new HashMap<String, Integer>();

	public List<Assignment> assigns = new ArrayList<>();
	
	public Map<Assignment,Integer> assignsHash= new HashMap<>(); 

	public List<Conjunction> conjs = new ArrayList<>();
	
	public Map<Conjunction,Integer> conjsHash= new HashMap<>(); 
	
	public List<Term> terms = new ArrayList<>();

//	public ConcurrentSkipListMap<Integer,List<Integer>> conjRvs = new ConcurrentSkipListMap<>();
	
	public List<List<Integer>> conjRvs = new ArrayList<>();

	public List<ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>>> conjSzRvs = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < conjSzRvs.size(); i++) {
			ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> rvs = conjSzRvs.get(i);
			sb.append("size->").append(i);

			for (Entry<Integer, List<Pair<Integer, Boolean>>> entry : rvs.entrySet()) {
				sb.append("\n\t").append("term->").append(entry.getKey());
				List<Pair<Integer, Boolean>> cList = entry.getValue();
				for (Pair<Integer, Boolean> Pair : cList) {
					sb.append("\n\t\t").append("conj->(" + Pair._1() + "," + Pair._2() + ")");
				}
			}

			sb.append("\n");
		}
		return sb.toString();
	}

	public void show() {
		for (Conjunction conj : conjs) {
			System.out.println("conj-->" + conj.getId() + "," + conj.getSize());
			for (Integer assignId : conj.getAssigns()) {
				Assignment assignment = assigns.get(assignId);
				System.out.println("\tassign-->" + assignment.getId() + "," + assignment.isBelong());
				for (Integer termId : assignment.getTerms()) {
					Term term = terms.get(termId);
					System.out.println("\t\tterm-->" + term.getId());
				}
			}
			System.out.println();
		}
	}

	public List<Assignment> getAssignments() {
		return assigns;
	}

	public void add(Assignment assignment) {
		assigns.add(assignment);
		
		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);
		
		jedis.sadd(assign_key.getBytes(), BinaryUtils.assignToByte(assignment));
		
		jedis.close();
	}

	public void add(Conjunction conjunction) {
		conjs.add(conjunction);
		
		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);
		
		jedis.sadd(conj_key.getBytes(), BinaryUtils.conjToByte(conjunction));
		
		jedis.close();
	}

	public void add(Term term) {
		terms.add(term);
		
		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);
		
		jedis.sadd(term_key.getBytes(), BinaryUtils.termToByte(term));
		
		jedis.close();
	}
	
	public Integer putDictionary(String key) {
		return dictionary.put(key);
	}
	
	public Integer getDictionary(String key) {
		return dictionary.get(key);
	}

	public void flush() {
		try {
			dictionary.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
