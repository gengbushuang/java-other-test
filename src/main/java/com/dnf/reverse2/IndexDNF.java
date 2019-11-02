package com.dnf.reverse2;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.dnf.Cond;
import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Conjunction;
import com.dnf.reverse2.model.Doc;
import com.dnf.reverse2.model.Term;
import com.dnf.reverse2.query.BoolQuery;

import sun.misc.Queue;

public class IndexDNF {

	public void appendIndex(Index index, String dnf, int id) {
		try {
			Doc doc = parse(dnf, index);
			doc.setId(id);
			index.add(doc);
			for (Integer conjId:doc.getConjs()) {
				index.conjReverse1(conjId, doc.getId());
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public Doc parse(String dnf, Index index) throws IOException, InterruptedException {
		StreamTokenizer st = new StreamTokenizer(new StringReader(dnf));
		boolean allDone = false;
		List<Conjunction> conjunctions = new LinkedList<>();
		Queue<String> queue = new Queue<>();
		while (true) {
			if (allDone) {
				break;
			}
			int token = st.nextToken();

			switch (token) {

			case StreamTokenizer.TT_WORD:
				queue.enqueue(st.sval);
				break;
			case StreamTokenizer.TT_NUMBER:
				queue.enqueue(String.valueOf((int) st.nval));
				break;
			case StreamTokenizer.TT_EOF:
				allDone = true;
				break;
			case ')':
				Queue<String> args = new Queue<>();
				while (!queue.isEmpty()) {
					args.enqueue(queue.dequeue());
				}
				Conjunction conj = conjParse(args, index);
				conjunctions.add(conj);
				break;
			default:
				break;
			}
		}

		List<Integer> integers = new ArrayList<>(conjunctions.size());
		for (Conjunction conjunction : conjunctions) {
			Integer integer = index.conjToInt(conjunction);
			integers.add(integer);
			index.conjReverse2(conjunction);
		}
		Doc doc = new Doc(integers);

		return doc;
	}

	private Conjunction conjParse(Queue<String> args, Index index) throws InterruptedException {

		String key;
		String op;
		int size = 0;

		boolean bool = false;
		List<Assignment> assignments = new LinkedList<>();
		while (!args.isEmpty()) {
			key = args.dequeue();
			if (key.equals("or")) {
				continue;
			}
			op = args.dequeue();

			if (op.equals("in")) {
				bool = true;
				size++;
			} else if (op.equals("not")) {
				bool = false;
			}
			List<Term> terms = new LinkedList<>();
			while (true) {
				op = args.dequeue();
				if (op.equals("and")) {
					break;
				}

				Term term = new Term(key, op);
				terms.add(term);
				if (args.isEmpty()) {
					break;
				}
			}

			Assignment assignment = amtBuild(terms, index, bool);
			assignments.add(assignment);
		}

		return conjBuild(assignments, index, size);
	}

	private Conjunction conjBuild(List<Assignment> assignments, Index index, int size) {
		List<Integer> integers = new ArrayList<>(assignments.size());
		for (Assignment assignment : assignments) {
			Integer integer = index.assignToInt(assignment);
			integers.add(integer);
		}

		Conjunction conjunction = new Conjunction(integers, size);
		return conjunction;
	}

	private Assignment amtBuild(List<Term> terms, Index index, boolean belong) {

		List<Integer> integers = new ArrayList<>(terms.size());
		for (Term term : terms) {
			Integer integer = index.termToInt(term);
			integers.add(integer);
		}
		Assignment assignment = new Assignment(integers, belong);
		return assignment;
	}

	public static void main(String[] args) {
//		String[] docs = { "(age in {3} and state in {NY}) or (state in {CA} and gender in {M})",
//				"(age in {3} and gender in {F}) or (state not in {CA, NY})",
//				"(age in {3} and gender in {M} and state not in {CA}) or (state in {CA} and gender in {F})",
//				"(age in {3, 4}) or (state in {CA} and gender in {M})", "(state not in {CA, NY}) or (age in {3, 4})",
//				"(state not in {CA, NY}) or (age in {3} and state in {NY}) or (state in {CA} and gender in {M})",
//				"(age in {3} and state in {NY}) or (state in {CA} and gender in {F})" };
//		IndexDNF indexDNF = new IndexDNF();
//		Index index = new Index();
//		for (int i = 0; i < docs.length; i++) {
//			indexDNF.appendIndex(index, docs[i], i);
//		}
		
		Index index = new Index();
		
		for(int i = 0; i<40;i++) {
			long n = System.currentTimeMillis();
			Cond[] conds = { new Cond("state", "CA"), new Cond("age", "4"), new Cond("gender", "M") };
			BoolQuery boolQuery = new BoolQuery();
			int[] query = boolQuery.query(conds, index);
			System.out.println(System.currentTimeMillis()-n);
			System.out.println(Arrays.toString(query));
		}
	}
}
