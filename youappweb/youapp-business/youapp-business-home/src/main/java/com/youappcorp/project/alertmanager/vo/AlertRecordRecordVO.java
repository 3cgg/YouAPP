package com.youappcorp.project.alertmanager.vo;

import com.youappcorp.project.alertmanager.model.AlertRecordRecord;

import me.bunny.kernel._c.utils.JObjectUtils;

public class AlertRecordRecordVO extends AlertRecordRecord {

	public AlertRecordRecordVO toAlertRecordRecordVO(){
		return JObjectUtils.simpleCopy(this, AlertRecordRecordVO.class);
	}
	
	
}
