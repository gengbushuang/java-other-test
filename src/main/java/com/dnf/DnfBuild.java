package com.dnf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.parse.ANTLRParser.id_return;

import com.ibm.icu.text.MessagePattern.Part;

public class DnfBuild {

	public void AddDoc(String name, String docid, String dnfDesc) {
		Doc doc = new Doc(name, docid, dnfDesc, new ArrayList<Integer>());
		String orStr;
		int conjId;
		Patr<String, Integer> patr;

		int i = UtilsDnf.skipSpace(dnfDesc, 0);
		while (true) {
			Patr<Integer, Integer> conjParse = conjParse(dnfDesc, i);
			conjId = conjParse._2();
			i = conjParse._1();
			doc.conjs.add(conjId);

			i = UtilsDnf.skipSpace(dnfDesc, i + 1);
			if (i >= dnfDesc.length()) {
				break;
			}

			patr = UtilsDnf.getString(dnfDesc, i);
			orStr = patr._1();
			i = patr._2();
			// 判断 or
			i = UtilsDnf.skipSpace(dnfDesc, i + 1);
		}

		int docInternalId = handler.add(doc);

		handler.conjReverse1(docInternalId, doc.conjs.toArray(new Integer[0]));
	}

	public int[] search(Cond[] conds) {
		List<Integer> terms = new ArrayList<>();

		for (int i = 0; i < conds.length; i++) {
			Integer id = handler.termMap.get(conds[i].getKey() + "%" + conds[i].getVal());
			if (id != null) {
				terms.add(id);
			}
		}
		if (terms.isEmpty()) {
			return new int[0];
		}

		int[] array = terms.stream().mapToInt(x -> x.intValue()).toArray();

		return handler.doSearch(array);
	}

	private Handler handler = new Handler();

	public Patr<Integer, Integer> conjParse(String dnf, int i) {
		String key, val;
		List<String> vals = new ArrayList<>(2);
		String op;
		boolean bool = false;
		Conj conj = new Conj(new ArrayList<Integer>());

		Patr<String, Integer> patr;
		while (true) {
			i = UtilsDnf.skipSpace(dnf, i + 1);
			patr = UtilsDnf.getString(dnf, i);
			key = patr._1();
			i = patr._2();

			i = UtilsDnf.skipSpace(dnf, i);
			patr = UtilsDnf.getString(dnf, i);
			op = patr._1();
			i = patr._2();

			if (op.equals("in")) {
				bool = true;
			} else {
				// 判断是否not
				i = UtilsDnf.skipSpace(dnf, i);

				patr = UtilsDnf.getString(dnf, i);
				op = patr._1();
				i = patr._2();

				if (op.equals("in")) {
					bool = false;
				}
			}

			// 下面是assignment
			i = UtilsDnf.skipSpace(dnf, i);
			// 判断{
			// if (dnf.charAt(i) == '\173') {
			//
			// }

			while (true) {
				i = UtilsDnf.skipSpace(dnf, i + 1);
				patr = UtilsDnf.getString(dnf, i);
				i = patr._2();
				val = patr._1();
				vals.add(val);

				i = UtilsDnf.skipSpace(dnf, i);
				// 判断}
				if (dnf.charAt(i) == '\175') {
					break;
				}
			}

			int amtId = amtBuild(key, vals.toArray(new String[0]), bool);
			conj.amtList.add(amtId);
			if (bool) {
				conj.size++;
			}

			i = UtilsDnf.skipSpace(dnf, i + 1);
			if (dnf.charAt(i) == '\051') { // 判断)

				int conjId = handler.add(conj);
				// 第二层倒排
				handler.conjReverse2(conj);
				return new Patr<Integer, Integer>(i, conjId);
			}
			patr = UtilsDnf.getString(dnf, i);
			i = patr._2();
			val = patr._1();
			// 判断是否 and
		}
	}

	public int amtBuild(String key, String[] vals, boolean belong) {
		Amt amt = new Amt(new ArrayList<Integer>(), belong);
		for (String val : vals) {
			Term term = new Term(key, val);
			int tid = handler.add(term);
			amt.terms.add(tid);
		}
		return handler.add(amt);
	}

	/*
	 * doc: (age ∈ { 3, 4 } and state ∈ { NY } ) or ( state ∈ { CA } and gender ∈ {
	 * M } ) -->
	 * 
	 * conj1: (age ∈ { 3, 4 } and state ∈ { NY } )
	 * 
	 * conj2: ( state ∈ { CA } and gender ∈ { M } )
	 */
	private class Doc {

		private int id;
		private String docId;
		private String name;
		private String dnf;
		private boolean conjSorted;
		private List<Integer> conjs;
		private boolean active;

		public Doc(String name2, String docid2, String dnfDesc, ArrayList<Integer> arrayList) {
			this.name = name2;
			this.docId = docid2;
			this.dnf = dnfDesc;
			this.conjs = arrayList;
		}
	}

	/**
	 * conjunction: age ∈ { 3, 4 } and state ∈ { NY } -->
	 * 
	 * assignment1: age ∈ { 3, 4 }
	 * 
	 * assignment2: state ∈ { NY }
	 * 
	 * @author gengbushuang
	 *
	 */
	private class Conj {
		private int id;
		private int size;
		private boolean amtSorted;
		private int[] amts;
		private List<Integer> amtList;

		public Conj(List<Integer> amtList) {
			this.amtList = amtList;
		}
	}

	/**
	 * assignment: age ∈ { 3, 4 } -->
	 * 
	 * term1: age ∈ { 3 }
	 * 
	 * term2: age ∈ { 4 }
	 * 
	 * @author gengbushuang
	 *
	 */
	private class Amt {
		private int id;
		private boolean belong;
		private boolean termSorted;
		private List<Integer> terms;

		public Amt(List<Integer> terms, boolean belong) {
			this.terms = terms;
			this.belong = belong;
		}
	}

	private class Term {
		private int id;
		private String key;
		private String value;

		public Term(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	private class CPair implements Comparable<CPair> {
		private int conjId;
		private boolean bool;

		public CPair(int conjId, boolean bool) {
			this.conjId = conjId;
			this.bool = bool;
		}

		@Override
		public int compareTo(CPair o) {
			return conjId - o.conjId;
		}
	}

	private class TermRvs implements Comparable<TermRvs> {
		private int termId;
		private List<CPair> cList;

		public TermRvs(int termId, List<CPair> cList) {
			this.termId = termId;
			this.cList = cList;
		}

		@Override
		public int compareTo(TermRvs o) {
			return termId - o.termId;
		}

	}

	private class Handler {
		// docs_ *docList
		private List<Doc> docs_;
		private List<Conj> conjs_;
		private List<Amt> amts_;
		private List<Term> terms_;

		private Map<String, Integer> termMap;

		private List<List<Integer>> conjRvs;

		private List<List<TermRvs>> conjSzRvs;

		public Handler() {
			this.docs_ = new ArrayList<>();
			this.conjs_ = new ArrayList<>();
			this.amts_ = new ArrayList<>();
			this.terms_ = new ArrayList<>();
			this.termMap = new HashMap<String, Integer>();

			this.conjRvs = new ArrayList<>();
			this.conjSzRvs = new ArrayList<>();
		}

		public int add(Doc doc) {
			doc.id = docs_.size();
			doc.active = true;
			// 排序
			docs_.add(doc);

			return doc.id;
		}

		public int add(Conj conj) {
			for (int i = 0; i < conjs_.size(); i++) {
				if (conjs_.get(i).equals(conj)) {
					return i;
				}
			}

			conj.id = conjs_.size();
			conjs_.add(conj);

			conjRvs.add(new ArrayList<Integer>());

			return conj.id;
		}

		public int add(Amt amt) {
			for (int i = 0; i < amts_.size(); i++) {
				if (amts_.get(i).equals(amt)) {
					return i;
				}
			}

			amt.id = amts_.size();
			amts_.add(amt);

			return amt.id;
		}

		public int add(Term term) {
			String key = term.key + "%" + term.value;
			if (termMap.containsKey(key)) {
				return termMap.get(key);
			}

			term.id = terms_.size();
			terms_.add(term);

			termMap.put(key, term.id);

			return term.id;
		}

		/**
		 * 倒排1
		 * 
		 * @param docId
		 * @param conjIds
		 */
		public void conjReverse1(int docId, Integer[] conjIds) {
			int rvsLen = conjRvs.size();
			for (int i = 0; i < conjIds.length; i++) {
				if (rvsLen <= conjIds[i]) {
					System.exit(1);
				}
				List<Integer> rvsDocList = conjRvs.get(conjIds[i]);
				Collections.sort(rvsDocList);
				int post = Collections.binarySearch(rvsDocList, docId);
				if (post > 0 && post < rvsDocList.size() && rvsDocList.get(post) == docId) {
					return;
				}

				rvsDocList.add(docId);
			}
		}

		/**
		 * 倒排2
		 * 
		 * @param conj
		 */
		public void conjReverse2(Conj conj) {
			if (conj.size >= conjSzRvs.size()) {
				resizeConjSzRvs(conj.size + 1);
			}
			List<TermRvs> termRvsList = conjSzRvs.get(conj.size);
			if (termRvsList == null) {
				termRvsList = new ArrayList<TermRvs>();
			}

			List<Integer> amtList = conj.amtList;
			for (int amtId : amtList) {
				insertTermRvsList(conj.id, amtId, termRvsList);
			}

			if (conj.size == 0) {
				insertTermRvsListEmptySet(conj.id);
			}
		}

		private void insertTermRvsListEmptySet(int cid) {
			List<TermRvs> termrvslist = conjSzRvs.get(0);
			List<CPair> cList = termrvslist.get(0).cList;
			insertClist(cid, true, cList);
		}

		public void insertTermRvsList(int conjId, int amtId, List<TermRvs> list) {
			Amt amt = amts_.get(amtId);

			List<Integer> terms = amt.terms;
			for (int i = 0; i < terms.size(); i++) {

				int ids = Collections.binarySearch(list, new TermRvs(terms.get(i), null));
				if (ids > -1) {
					List<CPair> cList = list.get(ids).cList;
					if (cList == null) {
						list.get(ids).cList = new ArrayList<>();
						cList = list.get(ids).cList;
					}
					insertClist(conjId, amt.belong, cList);
				} else {
					List<CPair> cList = new ArrayList<>();
					cList.add(new CPair(conjId, amt.belong));
					list.add(new TermRvs(terms.get(i), cList));
					Collections.sort(list);
				}
			}
		}

		public void insertClist(int conjId, boolean belong, List<CPair> l) {
			CPair cPair = new CPair(conjId, belong);
			int idx = Collections.binarySearch(l, cPair);
			if (idx > -1) {
				return;
			}
			l.add(cPair);
			Collections.sort(l);
		}

		public void resizeConjSzRvs(int size) {
			// size = upperPowerOfTwo(size);
			for (int i = conjSzRvs.size(); i < size; i++) {
				conjSzRvs.add(new ArrayList<>());
			}
		}

		public int upperPowerOfTwo(int size) {
			int a = 4;
			for (; a < size && a > 1;) {
				a = a << 1;
			}
			return a;
		}

		public int[] doSearch(int[] terms) {
			int[] conjs = getConjs(terms);
			if (conjs == null || conjs.length == 0) {
				return new int[0];
			}
			return getDocs(conjs);
		}

		private int[] getDocs(int[] conjs) {
			IntSet intSet = new IntSet();

			for (int conj : conjs) {
				if (conj >= conjRvs.size()) {
					continue;
				}
				List<Integer> doclist = conjRvs.get(conj);
				if (doclist == null || doclist.isEmpty()) {
					continue;
				}
				for (int doc : doclist) {
					intSet.Add(doc);
				}

			}
			return intSet.ToSlice();
		}

		private int[] getConjs(int[] terms) {
			int n = terms.length;
			if (conjSzRvs.size() <= 0) {
				System.exit(1);
			}
			if (n >= conjSzRvs.size()) {
				n = conjSzRvs.size() - 1;
			}

			IntSet intSet = new IntSet();

			for (int i = 0; i <= n; i++) {
				List<TermRvs> termlist = conjSzRvs.get(i);
				if (termlist == null || termlist.isEmpty()) {
					continue;
				}

				CountSet countSet = new CountSet(i);

				for (Integer term : terms) {
					int ids = Collections.binarySearch(termlist, new TermRvs(term, null));
					if (ids > -1 && termlist.get(ids).termId == ids && termlist.get(ids).cList != null) {
						List<CPair> cList = termlist.get(ids).cList;
						for (CPair cpPair : cList) {
							countSet.add(cpPair.conjId, cpPair.bool);
						}
					}
				}

				if (i == 0) {
					List<CPair> cList = termlist.get(0).cList;
					for (CPair cpPair : cList) {
						if (cpPair.bool) {
							countSet.add(cpPair.conjId, cpPair.bool);
						}
					}
				}

				intSet.AddSlice(countSet.ToSlice());
			}

			return intSet.ToSlice();
		}
	}

	public static void main(String[] args) {
		String s = "(age in {3} and state in {NY}) or (state in {CA} and gender in {M})";
		System.out.println(s.length());
		DnfBuild build = new DnfBuild();
		build.AddDoc("test", "1101", s);
	}
}
