package com.youappcorp.project.alertmanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.alertmanager.jpa.AlertRecordJPARepo;
import com.youappcorp.project.alertmanager.model.AlertRecord;

@Service(value="InternalAlertRecordServiceImpl.transation.jpa")
public class InternalAlertRecordServiceImpl extends InternalServiceSupport<AlertRecord>{

	@Autowired
	private AlertRecordJPARepo repo;
	
	@Override
	public JIPersist<?, AlertRecord, String> getRepo() {
		return repo;
	}

}
