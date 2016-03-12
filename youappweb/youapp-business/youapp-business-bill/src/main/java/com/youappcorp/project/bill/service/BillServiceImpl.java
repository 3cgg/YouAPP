/**
 * 
 */
package com.youappcorp.project.bill.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.bill.mapper.BillMapper;
import com.youappcorp.project.bill.model.Bill;

/**
 * @author Administrator
 *
 */
@Service(value="billService.transation")
public class BillServiceImpl extends ServiceSupport<Bill> implements BillService{

	@Autowired
	private BillMapper billMapper;
	
	@Override
	public void saveBill(ServiceContext context, Bill bill)
			throws JServiceException {
		saveOnly(context, bill);
	}

	@Override
	public void updateBill(ServiceContext context, Bill bill)
			throws JServiceException {
		updateOnly(context, bill);
	}

	@Override
	public Bill getBillById(ServiceContext context, String id) {
		return getById(context, id);
	}

	@Override
	public List<Bill> getBillsByPage(ServiceContext context, JPageable pagination) {
		return billMapper.getBillsByPage(pagination);
	}

	@Override
	public List<Bill> getBillByUserName(ServiceContext context, String userName) {
		return billMapper.getBillByUserName(userName);
	}

	@Override
	public JIPersist<?, Bill> getRepo() {
		return billMapper;
	}

}
