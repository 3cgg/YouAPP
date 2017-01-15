package com.youappcorp.project.alertmanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.alertmanager.jpa.AlertItemJPARepo;
import com.youappcorp.project.alertmanager.model.AlertItem;

@Service(value="InternalAlertItemServiceImpl.transation.jpa")
public class InternalAlertItemServiceImpl extends InternalServiceSupport<AlertItem>{

	@Autowired
	private AlertItemJPARepo repo;
	
	@Override
	public JIPersist<?, AlertItem, String> getRepo() {
		return repo;
	}

}
