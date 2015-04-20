/**
 * 
 */
package j.jave.framework.components.bill.service;

import j.jave.framework.components.bill.mapper.BillMapper;
import j.jave.framework.components.bill.model.Bill;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="billService")
public class BillServiceImpl extends ServiceSupport<Bill> implements BillService{

	@Autowired
	private BillMapper billMapper;
	
	@Override
	protected JMapper<Bill> getMapper() {
		return this.billMapper;
	}
	
	@Override
	public void saveBill(ServiceContext context, Bill bill)
			throws ServiceException {
		saveOnly(context, bill);
	}

	@Override
	public void updateBill(ServiceContext context, Bill bill)
			throws ServiceException {
		updateOnly(context, bill);
	}

	@Override
	public Bill getBillById(ServiceContext context, String id) {
		return getById(context, id);
	}

	@Override
	public List<Bill> getBillsByPage(ServiceContext context, Bill bill) {
		return billMapper.getBillsByPage(bill);
	}

	@Override
	public List<Bill> getBillByUserName(ServiceContext context, String userName) {
		return billMapper.getBillByUserName(userName);
	}

}
