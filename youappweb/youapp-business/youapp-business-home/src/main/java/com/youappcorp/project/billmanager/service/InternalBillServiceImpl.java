package com.youappcorp.project.billmanager.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.billmanager.jpa.BillJPARepo;
import com.youappcorp.project.billmanager.model.Bill;

@Service(value="internalBillServiceImpl.transation.jpa")
public class InternalBillServiceImpl extends InternalServiceSupport<Bill>{

	@Autowired
	private BillJPARepo billJPARepo;
	
	@Override
	public JIPersist<?, Bill, String> getRepo() {
		return billJPARepo;
	}

}
