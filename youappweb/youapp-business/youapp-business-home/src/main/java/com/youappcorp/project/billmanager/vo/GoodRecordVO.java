package com.youappcorp.project.billmanager.vo;

import com.youappcorp.project.billmanager.model.GoodRecord;

import me.bunny.kernel._c.utils.JObjectUtils;

public class GoodRecordVO extends GoodRecord {

	public GoodRecord toGoodRecord(){
		return JObjectUtils.simpleCopy(this, GoodRecord.class);
	}
	
}
