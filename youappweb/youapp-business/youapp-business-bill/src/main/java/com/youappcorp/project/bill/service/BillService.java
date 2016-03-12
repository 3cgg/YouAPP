/**
 * 
 */
package com.youappcorp.project.bill.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.bill.model.Bill;

/**
 * @author Administrator
 *
 */
public interface BillService extends Service<Bill> {
	
	/**
	 * 
	 * @bill context 
	 * @bill user
	 * @throws JServiceException
	 */
	public void saveBill(ServiceContext context, Bill bill) throws JServiceException;
	
	
	/**
	 * 
	 * @bill context
	 * @bill user
	 * @throws JServiceException
	 */
	public void updateBill(ServiceContext context, Bill bill) throws JServiceException;
	
	/**
	 * make the record not available
	 * @bill context
	 * @bill id
	 */
	public void delete(ServiceContext context, String id);
	
	/**
	 * get one .
	 * @bill id
	 * @return
	 */
	public Bill getBillById(ServiceContext context, String id);
	
	public List<Bill> getBillsByPage(ServiceContext context, JPageable pagination) ;
	
	public List<Bill> getBillByUserName(ServiceContext context, String userName);
	
	
}
