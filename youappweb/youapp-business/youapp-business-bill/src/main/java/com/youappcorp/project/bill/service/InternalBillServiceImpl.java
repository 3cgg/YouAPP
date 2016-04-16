package com.youappcorp.project.bill.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.bill.model.Bill;
import com.youappcorp.project.bill.repo.BillJPARepo;

@Service(value="internalBillServiceImpl.transation.jpa")
public class InternalBillServiceImpl extends ServiceSupport<Bill>{

	@Autowired
	private BillJPARepo billJPARepo;
	
	@Override
	public JIPersist<?, Bill> getRepo() {
		return billJPARepo;
	}

}
