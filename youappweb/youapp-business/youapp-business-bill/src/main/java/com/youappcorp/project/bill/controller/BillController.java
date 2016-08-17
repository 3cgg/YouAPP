package com.youappcorp.project.bill.controller;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

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
@RequestMapping(value="/bill/billmanager")
public class BillController extends SimpleControllerSupport{
	
	@Autowired
	private BillService billService;
	
	@RequestMapping(value="/saveBill")
	public ResponseModel saveBill(ServiceContext serviceContext,Bill bill) throws Exception {
		billService.saveBill(serviceContext, bill);
		return ResponseModel.newSuccess().setData(CREATE_SUCCESS);
	}
	
	@RequestMapping(value="/getBillById")
	public ResponseModel getBillById(ServiceContext serviceContext,String id) throws Exception {
		Bill bill= billService.getBillById(serviceContext,id);
		return ResponseModel.newSuccess().setData(bill);
	}
	
	@RequestMapping(value="/getBillByUserName")
	public ResponseModel getBillByUserName(ServiceContext serviceContext,String userName){
		List<Bill> bills=billService.getBillByUserName(serviceContext, userName);
		return ResponseModel.newSuccess().setData(bills);
	}
	
	@RequestMapping(value="/getAllBills")
	public ResponseModel getAllBills(ServiceContext serviceContext) throws Exception {
		JSimplePageable simplePageCriteria=new JSimplePageable();
		simplePageCriteria.setPageNumber(0);
		simplePageCriteria.setPageSize(100);
		JPage<Bill> billsPage=billService.getBillsByPage(serviceContext, null,simplePageCriteria);
		return ResponseModel.newSuccess().setData(billsPage);
	}
	
	@RequestMapping(value="/getBillsByPage")
	public ResponseModel getBillsByPage(ServiceContext serviceContext,BillSearchCriteria billSearchCriteria,JSimplePageable simplePageable){
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
		JPage<Bill> billsPage=billService.getBillsByPage(serviceContext, billSearchCriteria,simplePageable);
		return ResponseModel.newSuccess().setData(billsPage);
	}
	
	@RequestMapping(value="/deleteBillById")
	public ResponseModel deleteBillById(ServiceContext serviceContext,String id){
		billService.deleteBill(serviceContext, id); 
		return ResponseModel.newSuccess().setData(DELETE_SUCCESS);
	}

	@RequestMapping(value="/updateBill")
	public ResponseModel updateBill(ServiceContext serviceContext,Bill bill) throws JServiceException{
		Bill dbBill=billService.getBillById(serviceContext, bill.getId());
		dbBill.setMoney(bill.getMoney());
		dbBill.setGoodName(bill.getGoodName());
		dbBill.setGoodType(bill.getGoodType());
		dbBill.setBillTime(bill.getBillTime());
		dbBill.setMallCode(bill.getMallCode());
		dbBill.setMallName(bill.getMallName());
		dbBill.setDescription(bill.getDescription());
		dbBill.setVersion(bill.getVersion());
		billService.updateBill(serviceContext, dbBill);
		return ResponseModel.newSuccess().setData(UPDATE_SUCCESS);
	}
	
	
}
