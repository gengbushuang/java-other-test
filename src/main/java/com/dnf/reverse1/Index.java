package com.dnf.reverse1;

import com.dnf.model.Audience;

public interface Index {

	public void createIndex(Audience audience,IndexBuilder indexBuildr);
	
	public void queryIndex(com.dnf.reverse1.model.Query query,QueryBuilder queryBuilder);
	
	public void delIndex(Audience audience);
}
