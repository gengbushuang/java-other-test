package com.dnf.reverse2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Pair;
import com.dnf.reverse2.model.Term;

public class Index implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2188635111237107354L;

	public Map<String, Integer> termMap = new HashMap<String, Integer>();

	public List<Assignment> assigns = new ArrayList<>();

	public List<Conjunction> conjs = new ArrayList<>();

	public List<Term> terms = new ArrayList<>();

	public List<List<Integer>> conjRvs = new ArrayList<>();

	public List<ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>>> conjSzRvs = new ArrayList<>();

	@Override
	public String toString() {
		System.out.println(termMap);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < conjSzRvs.size(); i++) {
			ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> rvs = conjSzRvs.get(i);
			sb.append("size->").append(i);
			
			for(Entry<Integer, List<Pair<Integer, Boolean>>> entry :rvs.entrySet()) {
				sb.append("\n\t").append("term->").append(entry.getKey());
				List<Pair<Integer, Boolean>> cList = entry.getValue();
				for(Pair<Integer, Boolean> Pair:cList ) {
					sb.append("\n\t\t").append("conj->(" + Pair._1()+","+Pair._2()+")");
				}
			}
			
			sb.append("\n");
		}
		return sb.toString();
	}

}
