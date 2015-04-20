/**
 * 
 */
package j.jave.framework.components.bill.mapper;

import j.jave.framework.components.bill.model.Bill;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="BillMapper")
@JModelMapper(component="BillMapper",name=Bill.class)
public interface BillMapper extends JMapper<Bill> {
	
	public List<Bill> getBillByUserName(@Param(value="userName")String userName) ;
	
	public List<Bill> getBillsByPage(Bill bill);
	
	
}
