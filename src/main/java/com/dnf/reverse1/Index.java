package com.dnf.reverse1;

import com.dnf.model.Audience;

public interface Index {

	public void createIndex(Audience audience);
	
	public void queryIndex(Query query);
	
	public void delIndex(Audience audience);
}
