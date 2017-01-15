package com.youappcorp.project.alertmanager.vo;

import com.youappcorp.project.alertmanager.model.AlertItemRecord;

import me.bunny.kernel.jave.utils.JObjectUtils;

public class AlertItemRecordVO extends AlertItemRecord {

	public AlertItemRecordVO toAlertItemRecordVO(){
		return JObjectUtils.simpleCopy(this, AlertItemRecordVO.class);
	}
	
	
}
