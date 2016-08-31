/**
 * 
 */
package com.youappcorp.project.billmanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;

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
	 *  
	 * @billRecord user
	 * @throws JServiceException
	 */
	public void saveBill( BillRecord billRecord) throws BusinessException;
	
	/**
	 * 
	 * 
	 * @billRecord user
	 * @throws JServiceException
	 */
	public void updateBill( BillRecord billRecord) throws BusinessException;
	
	/**
	 * make the record not available
	 * 
	 * @billRecord id
	 */
	public void deleteBillById( String id);
	
	/**
	 * get one .
	 * @billRecord id
	 * @return
	 */
	public BillRecord getBillById( String id);
	
	public JPage<BillRecord> getBillsByPage( BillSearchCriteria billSearchCriteria, JPageable pagination) ;
	
	public List<BillRecord> getBillsByUserName( String userName);
	
	public List<BillRecord> getBillsByUserId( String userId);
	
	
	public void saveGood( GoodRecord goodRecord);
	
	public void updateGood( GoodRecord goodRecord);
	
	public void deleteGoodById( String id);
	
	public GoodRecord getGoodById( String id);
	
	public JPage<GoodRecord> getGoodsByPage( GoodSearchCriteria goodSearchCriteria , JPageable pagination) ;
	
	public List<GoodRecord> getGoodsByUserName( String userName);
	
	public List<GoodRecord> getGoodsByUserId( String userId);
	
	
}
