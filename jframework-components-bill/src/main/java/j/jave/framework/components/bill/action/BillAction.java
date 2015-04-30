package j.jave.framework.components.bill.action;

import j.jave.framework.components.bill.model.Bill;
import j.jave.framework.components.bill.model.BillSearchCriteria;
import j.jave.framework.components.bill.service.BillService;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.param.model.Param;
import j.jave.framework.components.param.service.ParamService;
import j.jave.framework.components.views.chart.SimpleBarChart;
import j.jave.framework.components.web.jsp.JSPAction;
import j.jave.framework.utils.JDateUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller(value="bill.billaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BillAction extends JSPAction {
	
	private Bill  bill;
	
	/**
	 * for search criteria 
	 */
	private BillSearchCriteria billSearchCriteria;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private ParamService paramService;
	
	
	public String toRecordBill() throws Exception {
		
		initSelect();
		
		return "/WEB-INF/jsp/bill/record-bill.jsp";
	}
	
	public String recordBill() throws Exception {
		billService.saveBill(getServiceContext(), bill);
		setSuccessMessage(CREATE_SUCCESS);
		
		initSelect();
		return "/WEB-INF/jsp/bill/record-bill.jsp";
	}
	
	
	public String toViewBill() throws Exception {
		
		String id=getParameter("id");
		Bill bill= billService.getBillById(getServiceContext(),id);
		if(bill!=null){
			setAttribute("bill", bill);
		}
		return "/WEB-INF/jsp/bill/view-bill.jsp"; 
	}
	
	public String toViewAllBill() throws Exception {
		BillSearchCriteria bill=new BillSearchCriteria();
		List<Bill> bills=billService.getBillsByPage(getServiceContext(), bill);
		setAttribute("bills", bills);
		return "/WEB-INF/jsp/bill/view-all-bill.jsp";
	}
	
	public String getBillsWithsCondition(){
		int latestMonth=36;
		if(billSearchCriteria!=null){
			latestMonth=billSearchCriteria.getLatestMonth();
		}
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1*latestMonth);
		
		if(billSearchCriteria==null){
			billSearchCriteria=new BillSearchCriteria();
		}
		
		billSearchCriteria.setBillTime(new Timestamp(calendar.getTime().getTime()));
		List<Bill> bills=billService.getBillsByPage(getServiceContext(), billSearchCriteria);
		setAttribute("bills", bills);
		return "/WEB-INF/jsp/bill/view-all-bill.jsp";
	}
	
	public String deleteBill(){
		billService.delete(getServiceContext(), getParameter("id")); 
		setSuccessMessage(DELETE_SUCCESS);
		return getBillsWithsCondition();
	}
	
	public String toEditBill(){
		Bill bill=billService.getBillById(getServiceContext(), getParameter("id"));
		setAttribute("bill", bill);
		initSelect();
		return "/WEB-INF/jsp/bill/edit-bill.jsp"; 
	}

	private void initSelect() {
		List<Param> goodTypes=paramService.getParamByFunctionId(getServiceContext(), "GOOD");
		setAttribute("goodCodes", goodTypes);
		
		List<Param> malls=paramService.getParamByFunctionId(getServiceContext(), "MALL");
		setAttribute("mallCodes", malls);
		
		List<Param> userNameCodes=paramService.getParamByFunctionId(getServiceContext(), "USERS");
		setAttribute("userNameCodes", userNameCodes);
	}
	
	public String editBill() throws ServiceException{
		Bill dbBill=billService.getBillById(getServiceContext(), bill.getId());
		dbBill.setMoney(bill.getMoney());
		dbBill.setGoodName(bill.getGoodName());
		dbBill.setGoodType(bill.getGoodType());
		dbBill.setBillTime(bill.getBillTime());
		dbBill.setMallCode(bill.getMallCode());
		dbBill.setMallName(bill.getMallName());
		dbBill.setUserCode(bill.getUserCode());
		dbBill.setDescription(bill.getDescription());
		dbBill.setVersion(bill.getVersion());
		billService.updateBill(getServiceContext(), dbBill);
		
		setAttribute("bill", billService.getBillById(getServiceContext(), bill.getId())); 
		setSuccessMessage(EDIT_SUCCESS);
		initSelect();
		return "/WEB-INF/jsp/bill/edit-bill.jsp";
		
	}
	
	public String toViewChart () throws ServiceException{
		
		if(billSearchCriteria==null){
			billSearchCriteria=new BillSearchCriteria();
		}
		SimpleBarChart simpleBarChart=new SimpleBarChart();
		List<Bill> bills=billService.getBillsByPage(getServiceContext(), billSearchCriteria);
		if(bills!=null){
			for(int i=0;i<bills.size();i++){
				Bill bill=bills.get(i);
				simpleBarChart.put(JDateUtils.format(bill.getBillTime()), bill.getGoodType(), bill.getMoney(),bill.getGoodTypeName());
			}
		}
		simpleBarChart.sort();
		setAttribute("barChart", simpleBarChart);
		return "/WEB-INF/jsp/bill/view-chart-bill.jsp";
		
	}
	
	
	public String toNavigate(){
		
		
		return "/WEB-INF/jsp/bill/navigate-bill.jsp";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
