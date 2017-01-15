package com.youappcorp.project.billmanager.vo;

import com.youappcorp.project.billmanager.model.BillRecord;

import me.bunny.kernel._c.utils.JObjectUtils;

public class BillRecordVO extends BillRecord {

	public BillRecord toBillRecord(){
		return JObjectUtils.simpleCopy(this, BillRecord.class);
	}
}
