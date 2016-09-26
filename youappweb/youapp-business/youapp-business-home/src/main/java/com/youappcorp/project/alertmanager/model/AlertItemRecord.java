package com.youappcorp.project.alertmanager.model;

import j.jave.kernal.jave.utils.JObjectUtils;

import j.jave.platform.data.web.model.JInputModel;
import j.jave.platform.data.web.model.JOutputModel;

import com.youappcorp.project.alertmanager.model.AlertItem;

public class AlertItemRecord extends AlertItem implements JInputModel , JOutputModel {

	public AlertItem toAlertItem(){
		return JObjectUtils.simpleCopy(this, AlertItem.class);
	}
	
	
}
