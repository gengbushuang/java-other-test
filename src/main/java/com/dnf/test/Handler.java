package com.dnf.test;

import java.util.HashMap;
import java.util.Map;

import com.dnf.test.model.Assignment;
import com.dnf.test.model.Conjunction;
import com.dnf.test.model.Doc;
import com.dnf.test.model.Term;
import com.dnf.test.model.TermRvs;
import com.dnf.test.struct.IntArray;
import com.dnf.test.struct.IntIntArray;
import com.dnf.test.struct.IntObjectArray;
import com.dnf.test.struct.ObjectArray;


public class Handler {
	//
	private ObjectArray<Doc> docs_;

	private ObjectArray<Conjunction> conjs_;

	private ObjectArray<Assignment> amts_;

	private ObjectArray<Term> terms_;

	private Map<String, Integer> termMap;

	private IntIntArray conjRvs;

	private IntObjectArray conjSzRvs;

	public Handler() {
		//
		this.docs_ = new ObjectArray<>();
		this.conjs_ = new ObjectArray<>();
		this.amts_ = new ObjectArray<>();
		this.terms_ = new ObjectArray<>();

		this.termMap = new HashMap<>();

		this.conjRvs = new IntIntArray();

		this.conjSzRvs = new IntObjectArray();

	}

	public void add(Doc doc) {

	}

	public Conjunction add(Conjunction conjunction) {
		// 查询Conjunction是否存在
		for (int i = 0; i < conjs_.size(); i++) {
			if (conjs_.get(i).equals(conjunction)) {
				return conjs_.get(i);
			}
		}
		// ConjID的生成
		int conjId = conjs_.size();
		Conjunction newConj = new Conjunction(conjId, conjunction.getAmts());
		conjs_.add(newConj);

		conjRvs.add(new IntArray());

		return newConj;
	}

	public Assignment add(Assignment assignment) {
		// 查询Assignment是否存在
		for (int i = 0; i < amts_.size(); i++) {
			if (amts_.get(i).equals(assignment)) {
				return amts_.get(i);
			}
		}
		// AmtID的生成
		int amtId = amts_.size();
		Assignment newAmt = new Assignment(amtId, assignment.getTerms(), assignment.isEliminate());
		amts_.add(newAmt);

		return newAmt;
	}

	public Term add(Term term) {
		// 查询Term是否存在
		String key = term.getKey() + "%" + term.getValue();
		if (termMap.containsKey(key)) {
			return new Term(termMap.get(key), term.getKey(), term.getValue());
		}

		// TermID的生成
		int termId = terms_.size();
		Term newTerm = new Term(termId, term.getKey(), term.getValue());
		terms_.add(newTerm);

		termMap.put(key, newTerm.getId());
		return newTerm;
	}

	public void conjReverse2(Conjunction conjunction) {
		if (conjunction.getSize() >= conjSzRvs.getSize()) {
			resizeConjSzRvs(conjunction.getSize() + 1);
		}

		
		ObjectArray<TermRvs> objectArray = conjSzRvs.get(conjunction.getSize());
		
		IntArray amtList = conjunction.getAmts();
		for (int i = 0; i < amtList.size(); i++) {
			int amtId = amtList.get(i);
			insertTermRvsList(conjunction, amtId, objectArray);
		}
	}

	private void insertTermRvsList(Conjunction conjunction, int amtId, ObjectArray<TermRvs> termRvss) {
		Assignment assignment = amts_.get(amtId);

		IntArray terms = assignment.getTerms();

		for (int i = 0; i < terms.size(); i++) {
			int amt_id = terms.get(i);
//			int ids = termRvss.search(amt_id);
//			if (ids > -1) {
//				
//			}
		}
	}

	private void resizeConjSzRvs(int size) {
		for (int i = 0; i < size; i++) {
			conjSzRvs.add(i, new ObjectArray<>());
		}
	}
}
