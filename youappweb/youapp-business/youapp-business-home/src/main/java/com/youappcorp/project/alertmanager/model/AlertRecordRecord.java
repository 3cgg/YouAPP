package com.youappcorp.project.alertmanager.model;

import me.bunny.app._c.data.web.model.JInputModel;
import me.bunny.app._c.data.web.model.JOutputModel;
import me.bunny.kernel._c.utils.JObjectUtils;

import com.youappcorp.project.alertmanager.model.AlertRecord;

public class AlertRecordRecord extends AlertRecord implements JInputModel , JOutputModel {

	public AlertRecord toAlertRecord(){
		return JObjectUtils.simpleCopy(this, AlertRecord.class);
	}
	
	
}
