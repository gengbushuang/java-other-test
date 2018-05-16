package com.dnf.reverse1;

import java.util.ArrayList;
import java.util.List;

import com.dnf.model.Audience;

public class IndexCreate {

	public List<Index> indexs = new ArrayList<Index>();

	public void createIndex(Audience audience) {
		for (Index index : indexs) {
			index.createIndex(audience);
		}
	}
}
