package com.youappcorp.project.alertmanager.vo;

import j.jave.kernal.jave.utils.JObjectUtils;

import com.youappcorp.project.alertmanager.model.AlertRecordRecord;

public class AlertRecordRecordVO extends AlertRecordRecord {

	public AlertRecordRecordVO toAlertRecordRecordVO(){
		return JObjectUtils.simpleCopy(this, AlertRecordRecordVO.class);
	}
	
	
}
