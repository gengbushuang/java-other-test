package com.dnf.reverse2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import com.dnf.model.ConstantKey;
import com.dnf.reverse1.ReidsDb;
import com.dnf.reverse2.comparator.AssignmentComparator;
import com.dnf.reverse2.comparator.ConjunctionComparator;
import com.dnf.reverse2.comparator.DocComparator;
import com.dnf.reverse2.comparator.PairComparator;
import com.dnf.reverse2.comparator.TermComparator;
import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Doc;
import com.dnf.reverse2.model.Pair;
import com.dnf.reverse2.model.Term;
import com.dnf.reverse2.struct.Dictionary;
import com.dnf.reverse2.struct.ListStruct;
import com.dnf.reverse2.util.BinaryUtils;

import redis.clients.jedis.Jedis;

public class Index {
	static PairComparator pairComparator = new PairComparator();
	static AssignmentComparator assignmentComparator = new AssignmentComparator();
	static TermComparator termComparator = new TermComparator();
	static ConjunctionComparator conjunctionComparator = new ConjunctionComparator();
	static DocComparator docComparator = new DocComparator();

	public String assign_key = ConstantKey.BASEKEY + "assign:";

	public String conj_key = ConstantKey.BASEKEY + "conj:";

	public String term_key = ConstantKey.BASEKEY + "term:";

	public String doc_key = ConstantKey.BASEKEY + "doc:";

	Dictionary dictionary;

	ListStruct<Assignment> assigns;

	ListStruct<Conjunction> conjs;

	ListStruct<Doc> docs;

	// private List<List<Integer>> conjRvs = new ArrayList<>();

	private ConcurrentSkipListMap<Integer, List<Integer>> conjRvs = new ConcurrentSkipListMap<>();
	//
	private List<ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>>> conjSzRvs = new ArrayList<>(5);

	public Index() {
		Jedis jedis = ReidsDb.DB().getJedis();
		jedis.select(1);

		loadDoc(jedis);
		loadConj(jedis);
		loadTerm(jedis);
		loadAssign(jedis);

		jedis.close();

		createIndex();

	}

	private void createIndex() {
		for (Doc doc : docs) {
			List<Integer> conjIds = doc.getConjs();
			for (Integer conjId : conjIds) {
				conjReverse1(conjId, doc.getId());
				int index = conjs.search(new Conjunction(null, 0, conjId), conjunctionComparator);
				if (index < 0) {
					continue;
				}
				Conjunction conj = conjs.indexGet(index);
				conjReverse2(conj);
			}
		}
	}

	private void loadDoc(Jedis jedis) {
		Set<byte[]> smembers = jedis.smembers(doc_key.getBytes());
		docs = new ListStruct<>(smembers.size() == 0 ? 10 : smembers.size());
		for (byte[] bs : smembers) {
			Doc doc = BinaryUtils.byteToDoc(bs);
			docs.add(doc);
		}

		docs.sort(docComparator);
	}

	private void loadAssign(Jedis jedis) {
		Set<byte[]> smembers = jedis.smembers(assign_key.getBytes());
		assigns = new ListStruct<>(smembers.size() == 0 ? 10 : smembers.size());
		for (byte[] bs : smembers) {
			Assignment assignment = BinaryUtils.byteToAssign(bs);
			assigns.add(assignment);
		}

		assigns.sort(assignmentComparator);

	}

	private void loadTerm(Jedis jedis) {
		Set<byte[]> smembers = jedis.smembers(term_key.getBytes());
		dictionary = new Dictionary(smembers.size() == 0 ? 10 : smembers.size());
		for (byte[] bs : smembers) {
			Term term = BinaryUtils.byteToTerm(bs);
			dictionary.add(term);
		}

		dictionary.sort(termComparator);
	}

	private void loadConj(Jedis jedis) {
		Set<byte[]> smembers = jedis.smembers(conj_key.getBytes());
		conjs = new ListStruct<>(smembers.size() == 0 ? 10 : smembers.size());
		for (byte[] bs : smembers) {
			Conjunction conj = BinaryUtils.byteToConj(bs);
			conjs.add(conj);
		}
		conjs.sort(conjunctionComparator);
	}

	// public List<List<Integer>> getConjRvs() {
	// return conjRvs;
	// }

	public ConcurrentSkipListMap<Integer, List<Integer>> getConjRvs() {
		return conjRvs;
	}

	//
	public List<ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>>> getConjSzRvs() {
		return conjSzRvs;
	}

	public Integer termToInt(Term term) {
		Integer integer = dictionary.get(term.getKey(), term.getValue());
		if (integer == null) {
			Term toMax = dictionary.listToMax();
			if (toMax == null) {
				integer = new Integer(1);
			} else {
				integer = new Integer(toMax.getId() + 1);
			}
			term.setId(integer);
			dictionary.add(term);
			// dictionary.add(term, term_key, t -> BinaryUtils.termToByte(t));
		}
		return integer;
	}

	public Integer assignToInt(Assignment assignment) {
		Integer integer = assigns.mapToInteger(assignment);
		if (integer == null) {
			Assignment toMax = assigns.listToMax();
			if (toMax == null) {
				integer = new Integer(0);
			} else {
				integer = new Integer(toMax.getId() + 1);
			}
			assignment.setId(integer);
			assigns.add(assignment);
			// assigns.add(assignment, assign_key, assign ->
			// BinaryUtils.assignToByte(assign));
		}
		return integer;
	}

	public Integer conjToInt(Conjunction conjunction) {
		Integer integer = conjs.mapToInteger(conjunction);
		if (integer == null) {
			Conjunction toMax = conjs.listToMax();
			if (toMax == null) {
				integer = new Integer(0);
			} else {
				integer = new Integer(toMax.getId() + 1);
			}
			conjunction.setId(integer);
			conjs.add(conjunction);
			// conjs.add(conjunction, conj_key, conj -> BinaryUtils.conjToByte(conj));
		}
		return integer;
	}

	public void add(Doc doc) {

		int index = docs.search(doc, docComparator);
		if (index > -1) {
			return;
		}
		docs.add(doc);
		// docs.add(doc, doc_key, d -> BinaryUtils.docToByte(d));
	}

	public Integer queryToTermId(String key, String value) {
		return dictionary.get(key, value);
	}

	/**
	 * 一级倒排
	 * 
	 * @param conjId
	 * @param adid
	 */
	public void conjReverse1(Integer conjId, int adid) {
		int size = conjRvs.keySet().size();
		if (conjId >= size) {
			for (int i = size; i < conjId + 1; i++) {
				conjRvs.put(i, new ArrayList<>(10));
			}
		}
		List<Integer> rvsDocList = conjRvs.get(conjId);
		int post = Collections.binarySearch(rvsDocList, adid);

		if (post > 0 && post < rvsDocList.size() && rvsDocList.get(post) == adid) {
			return;
		}
		rvsDocList.add(adid);
		Collections.sort(rvsDocList);

	}

	/**
	 * 二级倒排
	 * 
	 * @param conjunction
	 */
	public void conjReverse2(Conjunction conjunction) {
		int size = conjunction.getSize();
		if (size >= conjSzRvs.size()) {
			resizeConjSzRvs(size + 1);
		}

		ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> termRvsList = conjSzRvs.get(size);
		if (termRvsList == null) {
			termRvsList = new ConcurrentSkipListMap<>();
		}

		List<Integer> assigns = conjunction.getAssigns();
		for (Integer assignId : assigns) {
			insertTermRvsList(conjunction.getId(), assignId, termRvsList);
		}

		if (size == 0) {
			insertTermRvsListEmptySet(conjunction.getId());
		}
	}

	private void resizeConjSzRvs(int size) {
		for (int i = conjSzRvs.size(); i < size; i++) {
			conjSzRvs.add(new ConcurrentSkipListMap<>());
		}
	}

	/**
	 * 无限情况的插入
	 * 
	 * @param conjId
	 */
	private void insertTermRvsListEmptySet(int conjId) {
		ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> termrvslist = conjSzRvs.get(0);

		List<Pair<Integer, Boolean>> list = termrvslist.get(-1);
		if (list == null) {
			list = new ArrayList<>();
			termrvslist.put(-1, list);
		}

		insertClist(conjId, true, list);
	}

	private void insertTermRvsList(int conjId, Integer assignId,
			ConcurrentSkipListMap<Integer, List<Pair<Integer, Boolean>>> termRvsList) {

		int index = assigns.search(new Assignment(null, false, assignId), assignmentComparator);
		if (index < 0) {
			return;
		}
		Assignment assignment = assigns.indexGet(index);

		List<Integer> terms = assignment.getTerms();

		for (Integer termId : terms) {
			List<Pair<Integer, Boolean>> list = termRvsList.get(termId);
			if (list == null) {
				list = new ArrayList<>();
				termRvsList.put(termId, list);
			}
			insertClist(conjId, assignment.isBelong(), list);
		}
	}

	private void insertClist(int conjId, boolean belong, List<Pair<Integer, Boolean>> list) {
		Pair<Integer, Boolean> patr = new Pair<Integer, Boolean>(conjId, belong);
		int idx = Collections.binarySearch(list, patr, pairComparator);
		if (idx > -1) {
			return;
		}
		list.add(patr);
		Collections.sort(list, pairComparator);
	}

	/**
	 * 删除
	 * 
	 * @param doc
	 */
	public void del(Doc doc) {
		int index_doc = docs.search(doc, docComparator);
		if (index_doc < 0) {
			return;
		}
		Doc indexGet = docs.indexGet(index_doc);

		List<Integer> conjsId = indexGet.getConjs();
		for (Integer conjId : conjsId) {
			List<Integer> list = conjRvs.get(conjId);
			int index_conj = Collections.binarySearch(list, indexGet.getId());
			if (index_conj < 0) {
				continue;
			}
			list.remove(index_conj);
		}

		docs.del(index_doc, doc_key, d -> BinaryUtils.docToByte(d));
	}

	
}
