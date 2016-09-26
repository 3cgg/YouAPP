package com.youappcorp.project.alertmanager.vo;

import j.jave.kernal.jave.utils.JObjectUtils;

import com.youappcorp.project.alertmanager.model.AlertItemRecord;

public class AlertItemRecordVO extends AlertItemRecord {

	public AlertItemRecordVO toAlertItemRecordVO(){
		return JObjectUtils.simpleCopy(this, AlertItemRecordVO.class);
	}
	
	
}
