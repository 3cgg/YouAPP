package com.youappcorp.project.alertmanager.model;

import j.jave.kernal.jave.utils.JObjectUtils;

import j.jave.platform.data.web.model.JInputModel;
import j.jave.platform.data.web.model.JOutputModel;

import com.youappcorp.project.alertmanager.model.AlertRecord;

public class AlertRecordRecord extends AlertRecord implements JInputModel , JOutputModel {

	public AlertRecord toAlertRecord(){
		return JObjectUtils.simpleCopy(this, AlertRecord.class);
	}
	
	
}
