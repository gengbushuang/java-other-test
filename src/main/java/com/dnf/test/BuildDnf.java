package com.dnf.test;

import java.util.ArrayList;
import java.util.List;

import com.dnf.Patr;
import com.dnf.UtilsDnf;
import com.dnf.test.model.Assignment;
import com.dnf.test.model.Conjunction;
import com.dnf.test.model.Doc;
import com.dnf.test.model.Term;
import com.dnf.test.struct.IntArray;


public class BuildDnf {

	Handler handler = new Handler();

	public void AddDoc(int id, String name, String dnfDesc) {
		Doc doc = new Doc(id, name, dnfDesc, new IntArray());
		String orStr;
		int conjId;
		Patr<String, Integer> patr;

		int i = UtilsDnf.skipSpace(dnfDesc, 0);
		while (true) {
			Patr<Integer, Integer> conjParse = conjParse(dnfDesc, i);
			conjId = conjParse._2();
			i = conjParse._1();
			doc.addConjId(conjId);

			i = UtilsDnf.skipSpace(dnfDesc, i + 1);
			if (i >= dnfDesc.length()) {
				break;
			}

			patr = UtilsDnf.getString(dnfDesc, i);
			orStr = patr._1();
			i = patr._2();
			// assert orStr.charAt(index)
			i = UtilsDnf.skipSpace(dnfDesc, i + 1);
		}

	}

	private Patr<Integer, Integer> conjParse(String dnf, int i) {

		String key, val;
		// List<String> vals = new ArrayList<>(2);
		String op;
		boolean bool = false;
		Conjunction conjunction = new Conjunction(new IntArray());

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
			assert dnf.charAt(i) == '\173';
			// if (dnf.charAt(i) == '\173') {
			//
			// }
			List<String> vals = new ArrayList<>(2);
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

			Assignment amtBuild = amtBuild(key, vals.toArray(new String[0]), bool);
			conjunction.addAmtId(amtBuild.getId());

			if (bool) {
				conjunction.addSize();
			}

			i = UtilsDnf.skipSpace(dnf, i + 1);
			if (dnf.charAt(i) == '\051') { // 判断)

				Conjunction add = handler.add(conjunction);
				// 第二层倒排
				handler.conjReverse2(add);
				return new Patr<Integer, Integer>(i, add.getId());
			}
			patr = UtilsDnf.getString(dnf, i);
			i = patr._2();
			val = patr._1();
			// 判断是否 and
		}
	}

	private Assignment amtBuild(String key, String[] array, boolean bool) {
		Assignment assignment = new Assignment(new IntArray(), bool);
		for (String val : array) {
			Term term = new Term(key, val);
			Term add = handler.add(term);
			assignment.addTermId(add.getId());
		}
		return handler.add(assignment);
	}
}
