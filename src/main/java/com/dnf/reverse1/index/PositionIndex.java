package com.dnf.reverse1.index;

import com.dnf.model.Audience;
import com.dnf.model.ConstantKey;
import com.dnf.reverse1.Index;
import com.dnf.reverse1.IndexBuilder;
import com.dnf.reverse1.QueryBuilder;
import com.dnf.reverse1.model.Query;

/**
 * 广告位索引
 * 
 * @author gengbushuang
 *
 */
public class PositionIndex implements Index {

	@Override
	public void createIndex(Audience audience, IndexBuilder indexBuildr) {
		String positionId = audience.getPositionId();
		String id = String.valueOf(audience.getId());
		indexBuildr.set(ConstantKey.AD_POSITION + positionId, String.valueOf(audience.getId()));

		// indexBuildr.zset(ConstantKey.AD_POSITION + positionId, audience.getId(), id);
		indexBuildr.positiveRow(id, ConstantKey.AD_POSITION + positionId);
	}

	@Override
	public void queryIndex(Query query, QueryBuilder queryBuilder) {
		String key_position = ConstantKey.AD_POSITION + query.getpId();
		String key_position_tmp = ConstantKey.AD_POSITION + "tmp";
		// 并集查询
		queryBuilder.sunion(key_position_tmp, key_position);
	}

	// 先注释掉,以后删除
	// @Override
	// public void delIndex(Audience audience, DelBuilder delBuilder) {
	// String positionId = audience.getPositionId();
	// delBuilder.del(ConstantKey.AD_POSITION + positionId,
	// String.valueOf(audience.getId()));
	// }

}
