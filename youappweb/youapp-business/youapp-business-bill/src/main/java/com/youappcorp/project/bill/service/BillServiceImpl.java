/**
 * 
 */
package com.youappcorp.project.bill.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.bill.model.Bill;
import com.youappcorp.project.bill.repo.BillJPARepo;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.service.UserManagerService;

/**
 * @author J
 *
 */
@Service(value="billService.transation.jpa")
public class BillServiceImpl extends ServiceSupport implements BillService{

	@Autowired
	private InternalBillServiceImpl internalBillServiceImpl;
	
	@Autowired
	private BillJPARepo billJPARepo;
	
	private UserManagerService userManagerService=
			JServiceHubDelegate.get().getService(this, UserManagerService.class);
	
	@Override
	public void saveBill(ServiceContext context, Bill bill)
			throws BusinessException {
		try{
			
			internalBillServiceImpl.saveOnly(context, bill);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateBill(ServiceContext context, Bill bill)
			throws BusinessException {
		try{
			internalBillServiceImpl.updateOnly(context, bill);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public Bill getBillById(ServiceContext context, String id) {
		return internalBillServiceImpl.getById(context, id);
	}

	@Override
	public JPage<Bill> getBillsByPage(ServiceContext context, JPageable pagination) {
		return internalBillServiceImpl.getsByPage(context,pagination);
	}

	@Override
	public List<Bill> getBillByUserName(ServiceContext context, String userName) {
		User user=  userManagerService.getUserByName(context, userName);
		if(user==null) return null;
		return billJPARepo.getBillByUserId(user.getId());
	}

	@Override
	public void deleteBill(ServiceContext context, String id) {
		internalBillServiceImpl.delete(context, id);
	}
	
}
