package com.youappcorp.project.alertmanager.model;

import me.bunny.app._c.data.web.model.JInputModel;
import me.bunny.app._c.data.web.model.JOutputModel;
import me.bunny.kernel._c.utils.JObjectUtils;

import com.youappcorp.project.alertmanager.model.AlertItem;

public class AlertItemRecord extends AlertItem implements JInputModel , JOutputModel {

	public AlertItem toAlertItem(){
		return JObjectUtils.simpleCopy(this, AlertItem.class);
	}
	
	
}
