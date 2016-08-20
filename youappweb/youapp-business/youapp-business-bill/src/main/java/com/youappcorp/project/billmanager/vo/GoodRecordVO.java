package com.youappcorp.project.billmanager.vo;

import j.jave.kernal.jave.utils.JObjectUtils;

import com.youappcorp.project.billmanager.model.GoodRecord;

public class GoodRecordVO extends GoodRecord {

	public GoodRecord toGoodRecord(){
		return JObjectUtils.simpleCopy(this, GoodRecord.class);
	}
	
}
