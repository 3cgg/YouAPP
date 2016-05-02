package com.youappcorp.project.bill.controller;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.platform.basicwebcomp.core.model.SimplePageCriteria;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerSupport;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youappcorp.project.bill.model.Bill;
import com.youappcorp.project.bill.model.BillSearchCriteria;
import com.youappcorp.project.bill.service.BillService;

@Controller
@RequestMapping(value="/bill/billcontroller")
public class BillController extends ControllerSupport{
	
	@Autowired
	private BillService billService;
	
	@RequestMapping(value="/saveBill")
	public ResponseModel saveBill(Bill bill) throws Exception {
		billService.saveBill(getServiceContext(), bill);
		return ResponseModel.newSuccess().setData(CREATE_SUCCESS);
	}
	
	@RequestMapping(value="/getBillById")
	public ResponseModel getBillById(String id) throws Exception {
		Bill bill= billService.getBillById(getServiceContext(),id);
		return ResponseModel.newSuccess().setData(bill);
	}
	
	@RequestMapping(value="/getBillByUserName")
	public ResponseModel getBillByUserName(String userName){
		List<Bill> bills=billService.getBillByUserName(getServiceContext(), userName);
		return ResponseModel.newSuccess().setData(bills);
	}
	
	@RequestMapping(value="/getAllBills")
	public ResponseModel getAllBills() throws Exception {
		SimplePageCriteria simplePageCriteria=new SimplePageCriteria();
		simplePageCriteria.setPageNumber(0);
		simplePageCriteria.setPageSize(100);
		JPage<Bill> billsPage=billService.getBillsByPage(getServiceContext(), simplePageCriteria);
		return ResponseModel.newSuccess().setData(billsPage);
	}
	
	@RequestMapping(value="/getBillsByCriteria")
	public ResponseModel getBillsByCriteria(BillSearchCriteria billSearchCriteria){
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
		JPage<Bill> billsPage=billService.getBillsByPage(getServiceContext(), billSearchCriteria);
		return ResponseModel.newSuccess().setData(billsPage);
	}
	
	@RequestMapping(value="/deleteBillById")
	public ResponseModel deleteBillById(String id){
		billService.deleteBill(getServiceContext(), id); 
		return ResponseModel.newSuccess().setData(DELETE_SUCCESS);
	}

	@RequestMapping(value="/updateBill")
	public ResponseModel updateBill(Bill bill) throws JServiceException{
		Bill dbBill=billService.getBillById(getServiceContext(), bill.getId());
		dbBill.setMoney(bill.getMoney());
		dbBill.setGoodName(bill.getGoodName());
		dbBill.setGoodType(bill.getGoodType());
		dbBill.setBillTime(bill.getBillTime());
		dbBill.setMallCode(bill.getMallCode());
		dbBill.setMallName(bill.getMallName());
		dbBill.setDescription(bill.getDescription());
		dbBill.setVersion(bill.getVersion());
		billService.updateBill(getServiceContext(), dbBill);
		return ResponseModel.newSuccess().setData(UPDATE_SUCCESS);
	}
	
	
}
