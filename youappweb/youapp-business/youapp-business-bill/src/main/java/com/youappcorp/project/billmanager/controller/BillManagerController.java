package com.youappcorp.project.billmanager.controller;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youappcorp.project.billmanager.model.BillRecord;
import com.youappcorp.project.billmanager.model.GoodRecord;
import com.youappcorp.project.billmanager.service.BillManagerService;
import com.youappcorp.project.billmanager.vo.BillRecordVO;
import com.youappcorp.project.billmanager.vo.BillSearchCriteria;
import com.youappcorp.project.billmanager.vo.GoodRecordVO;
import com.youappcorp.project.billmanager.vo.GoodSearchCriteria;

@Controller
@RequestMapping(value="/bill/billmanager")
public class BillManagerController extends SimpleControllerSupport{
	
	@Autowired
	private BillManagerService billManagerService;
	
	@RequestMapping(value="/saveBill")
	public ResponseModel saveBill(ServiceContext serviceContext,BillRecordVO billRecordVO) throws Exception {
		BillRecord billRecord=billRecordVO.toBillRecord();
		billManagerService.saveBill(serviceContext, billRecord);
		return ResponseModel.newSuccess(billRecord.getId());
	}
	
	@RequestMapping(value="/getBillById")
	public ResponseModel getBillById(ServiceContext serviceContext,String id) throws Exception {
		BillRecord billRecord= billManagerService.getBillById(serviceContext,id);
		return ResponseModel.newSuccess().setData(toBillRecordVO(billRecord));
	}

	private BillRecordVO toBillRecordVO(BillRecord billRecord) {
		return JObjectUtils.simpleCopy(billRecord, BillRecordVO.class);
	}
	
	private List<BillRecordVO> toBillRecordVOs(List<BillRecord> billRecords) {
		List<BillRecordVO> billRecordVOs=new ArrayList<BillRecordVO>();
		for(BillRecord billRecord:billRecords){
			billRecordVOs.add(toBillRecordVO(billRecord));
		}
		return billRecordVOs;
	}
	
	private void toBillRecordVOPage(JPage<BillRecord> billRecordsPage) {
		List<BillRecordVO> billRecordVOs=new ArrayList<BillRecordVO>();
		for(BillRecord billRecord:billRecordsPage.getContent()){
			billRecordVOs.add(toBillRecordVO(billRecord));
		}
		billRecordsPage.setContent(billRecordVOs);
	}
	
	@RequestMapping(value="/getBillsByUserName")
	public ResponseModel getBillsByUserName(ServiceContext serviceContext,String userName){
		List<BillRecord> billRecords=billManagerService.getBillsByUserName(serviceContext, userName);
		return ResponseModel.newSuccess().setData(toBillRecordVOs(billRecords));
	}
	
	@RequestMapping(value="/getBillsByPage")
	public ResponseModel getBillsByPage(ServiceContext serviceContext,BillSearchCriteria billSearchCriteria,JSimplePageable simplePageable){
//		int latestMonth=36;
//		if(billSearchCriteria!=null){
//			latestMonth=billSearchCriteria.getLatestMonth();
//		}
//		Calendar calendar=Calendar.getInstance();
//		calendar.add(Calendar.MONTH, -1*latestMonth);
//		
//		if(billSearchCriteria==null){
//			billSearchCriteria=new BillSearchCriteria();
//		}
//		billSearchCriteria.setBillTime(new Timestamp(calendar.getTime().getTime()));
		JPage<BillRecord> billRecordsPage=billManagerService.getBillsByPage(serviceContext, billSearchCriteria,simplePageable);
		toBillRecordVOPage(billRecordsPage);
		return ResponseModel.newSuccess().setData(billRecordsPage);
	}
	
	@RequestMapping(value="/deleteBillById")
	public ResponseModel deleteBillById(ServiceContext serviceContext,String id){
		billManagerService.deleteBillById(serviceContext, id); 
		return ResponseModel.newSuccess();
	}

	@RequestMapping(value="/updateBill")
	public ResponseModel updateBill(ServiceContext serviceContext,BillRecordVO billRecordVO) throws JServiceException{
		billRecordVO.toBillRecord();
		billManagerService.updateBill(serviceContext, billRecordVO);
		return ResponseModel.newSuccess();
	}
	
	
	
	
	
	
	@RequestMapping(value="/saveGood")
	public ResponseModel saveGood(ServiceContext serviceContext,GoodRecordVO goodRecordVO) throws Exception {
		GoodRecord goodRecord=goodRecordVO.toGoodRecord();
		billManagerService.saveGood(serviceContext, goodRecord);
		return ResponseModel.newSuccess(goodRecord.getId());
	}
	
	@RequestMapping(value="/getGoodById")
	public ResponseModel getGoodById(ServiceContext serviceContext,String id) throws Exception {
		GoodRecord goodRecord= billManagerService.getGoodById(serviceContext,id);
		return ResponseModel.newSuccess().setData(toGoodRecordVO(goodRecord));
	}

	private GoodRecordVO toGoodRecordVO(GoodRecord goodRecord) {
		return JObjectUtils.simpleCopy(goodRecord, GoodRecordVO.class);
	}
	
	private List<GoodRecordVO> toGoodRecordVOs(List<GoodRecord> billRecords) {
		List<GoodRecordVO> goodRecordVOs=new ArrayList<GoodRecordVO>();
		for(GoodRecord goodRecord:billRecords){
			goodRecordVOs.add(toGoodRecordVO(goodRecord));
		}
		return goodRecordVOs;
	}
	
	private void toGoodRecordVOPage(JPage<GoodRecord> billRecordsPage) {
		List<GoodRecordVO> goodRecordVOs=new ArrayList<GoodRecordVO>();
		for(GoodRecord goodRecord:billRecordsPage.getContent()){
			goodRecordVOs.add(toGoodRecordVO(goodRecord));
		}
		billRecordsPage.setContent(goodRecordVOs);
	}
	
	@RequestMapping(value="/getGoodsByUserName")
	public ResponseModel getGoodsByUserName(ServiceContext serviceContext,String userName){
		List<GoodRecord> billRecords=billManagerService.getGoodsByUserName(serviceContext, userName);
		return ResponseModel.newSuccess().setData(toGoodRecordVOs(billRecords));
	}
	
	@RequestMapping(value="/getGoodsByPage")
	public ResponseModel getGoodsByPage(ServiceContext serviceContext,GoodSearchCriteria goodSearchCriteria,JSimplePageable simplePageable){
//		int latestMonth=36;
//		if(goodSearchCriteria!=null){
//			latestMonth=goodSearchCriteria.getLatestMonth();
//		}
//		Calendar calendar=Calendar.getInstance();
//		calendar.add(Calendar.MONTH, -1*latestMonth);
//		
//		if(goodSearchCriteria==null){
//			goodSearchCriteria=new GoodSearchCriteria();
//		}
//		goodSearchCriteria.setGoodTime(new Timestamp(calendar.getTime().getTime()));
		JPage<GoodRecord> billRecordsPage=billManagerService.getGoodsByPage(serviceContext, goodSearchCriteria,simplePageable);
		toGoodRecordVOPage(billRecordsPage);
		return ResponseModel.newSuccess().setData(billRecordsPage);
	}
	
	@RequestMapping(value="/deleteGoodById")
	public ResponseModel deleteGoodById(ServiceContext serviceContext,String id){
		billManagerService.deleteGoodById(serviceContext, id); 
		return ResponseModel.newSuccess();
	}

	@RequestMapping(value="/updateGood")
	public ResponseModel updateGood(ServiceContext serviceContext,GoodRecordVO goodRecordVO) throws JServiceException{
		goodRecordVO.toGoodRecord();
		billManagerService.updateGood(serviceContext, goodRecordVO);
		return ResponseModel.newSuccess();
	}
	
}
