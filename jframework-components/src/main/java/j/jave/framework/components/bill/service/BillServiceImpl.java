/**
 * 
 */
package j.jave.framework.components.bill.service;

import j.jave.framework.components.bill.mapper.BillMapper;
import j.jave.framework.components.bill.model.Bill;
import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.AbstractBaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="billService")
public class BillServiceImpl extends AbstractBaseService implements BillService{

	@Autowired
	private BillMapper billMapper;
	
	@Override
	public void saveBill(ServiceContext context, Bill bill)
			throws ServiceException {
		proxyOnSave(billMapper, context.getUser(), bill);
	}

	@Override
	public void updateBill(ServiceContext context, Bill bill)
			throws ServiceException {
		proxyOnUpdate(billMapper, context.getUser(), bill);
	}

	@Override
	public void delete(ServiceContext context, String id) {
		billMapper.markDeleted(id);
	}

	@Override
	public Bill getBillById(ServiceContext context, String id) {
		return billMapper.get(id);
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
