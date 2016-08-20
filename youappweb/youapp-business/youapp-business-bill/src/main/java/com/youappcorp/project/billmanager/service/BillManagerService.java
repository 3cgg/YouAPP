/**
 * 
 */
package com.youappcorp.project.billmanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.billmanager.model.BillRecord;
import com.youappcorp.project.billmanager.model.GoodRecord;
import com.youappcorp.project.billmanager.vo.BillSearchCriteria;
import com.youappcorp.project.billmanager.vo.GoodSearchCriteria;

/**
 * @author J
 *
 */
public interface BillManagerService {
	
	/**
	 * 
	 * @billRecord serviceContext 
	 * @billRecord user
	 * @throws JServiceException
	 */
	public void saveBill(ServiceContext serviceContext, BillRecord billRecord) throws BusinessException;
	
	/**
	 * 
	 * @billRecord serviceContext
	 * @billRecord user
	 * @throws JServiceException
	 */
	public void updateBill(ServiceContext serviceContext, BillRecord billRecord) throws BusinessException;
	
	/**
	 * make the record not available
	 * @billRecord serviceContext
	 * @billRecord id
	 */
	public void deleteBillById(ServiceContext serviceContext, String id);
	
	/**
	 * get one .
	 * @billRecord id
	 * @return
	 */
	public BillRecord getBillById(ServiceContext serviceContext, String id);
	
	public JPage<BillRecord> getBillsByPage(ServiceContext serviceContext, BillSearchCriteria billSearchCriteria, JPageable pagination) ;
	
	public List<BillRecord> getBillsByUserName(ServiceContext serviceContext, String userName);
	
	public List<BillRecord> getBillsByUserId(ServiceContext serviceContext, String userId);
	
	
	public void saveGood(ServiceContext serviceContext, GoodRecord goodRecord);
	
	public void updateGood(ServiceContext serviceContext, GoodRecord goodRecord);
	
	public void deleteGoodById(ServiceContext serviceContext, String id);
	
	public GoodRecord getGoodById(ServiceContext serviceContext, String id);
	
	public JPage<GoodRecord> getGoodsByPage(ServiceContext serviceContext, GoodSearchCriteria goodSearchCriteria , JPageable pagination) ;
	
	public List<GoodRecord> getGoodsByUserName(ServiceContext serviceContext, String userName);
	
	public List<GoodRecord> getGoodsByUserId(ServiceContext serviceContext, String userId);
	
	
}
