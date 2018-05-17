package com.dnf.reverse1.index;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.DelBuilder;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.IndexBuilder;
import com.dnf.reverse1.QueryBuilder;
import com.dnf.reverse1.model.Query;

public class PositionIndex implements Index {

	public String fieldName() {
		return "po";
	}

	@Override
	public void createIndex(Audience audience, IndexBuilder indexBuildr) {
		String positionId = audience.getPositionId();
		indexBuildr.set(ConstantKey.AD_POSITION + positionId, String.valueOf(audience.getId()));
	}

	@Override
	public void queryIndex(Query query, QueryBuilder queryBuilder) {
		String key_position = ConstantKey.AD_POSITION + query.getpId();
		String key_position_tmp = ConstantKey.AD_POSITION + "tmp";
		queryBuilder.sunion(fieldName(), key_position_tmp, key_position);
	}

	@Override
	public void delIndex(Audience audience, DelBuilder delBuilder) {
		String positionId = audience.getPositionId();
		delBuilder.del(ConstantKey.AD_POSITION + positionId, String.valueOf(audience.getId()));
	}

}
