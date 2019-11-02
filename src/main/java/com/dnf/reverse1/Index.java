package com.dnf.reverse1;

import com.dnf.model.Audience;
import com.dnf.reverse1.model.Query;

public interface Index {

	public void createIndex(Audience audience,IndexBuilder indexBuildr);
	
	public void queryIndex(Query query,QueryBuilder queryBuilder);
	
	//先注释掉,以后删除
//	public void delIndex(Audience audience,DelBuilder delBuilder);
}
