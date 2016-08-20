package com.youappcorp.project.billmanager.vo;

import j.jave.kernal.jave.utils.JObjectUtils;

import com.youappcorp.project.billmanager.model.BillRecord;

public class BillRecordVO extends BillRecord {

	public BillRecord toBillRecord(){
		return JObjectUtils.simpleCopy(this, BillRecord.class);
	}
}
