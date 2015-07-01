/**
 * 
 */
package j.jave.framework.components.bill.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.components.bill.model.Bill;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;

import java.util.List;

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
	
	public List<Bill> getBillsByPage(ServiceContext context, JPagination pagination) ;
	
	public List<Bill> getBillByUserName(ServiceContext context, String userName);
	
	
}
