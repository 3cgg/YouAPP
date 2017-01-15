package com.youappcorp.project.billmanager.controller;

import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.utils.JObjectUtils;
import me.bunny.kernel.eventdriven.exception.JServiceException;

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
	public ResponseModel saveBill(BillRecordVO billRecordVO) throws Exception {
		BillRecord billRecord=billRecordVO.toBillRecord();
		billManagerService.saveBill( billRecord);
		return ResponseModel.newSuccess(billRecord.getId());
	}
	
	@RequestMapping(value="/getBillById")
	public ResponseModel getBillById(String id) throws Exception {
		BillRecord billRecord= billManagerService.getBillById(id);
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
		billRecordsPage.setContent(toBillRecordVOs(billRecordsPage.getContent()));
	}
	
	@RequestMapping(value="/getBillsByUserName")
	public ResponseModel getBillsByUserName(String userName){
		List<BillRecord> billRecords=billManagerService.getBillsByUserName( userName);
		return ResponseModel.newSuccess().setData(toBillRecordVOs(billRecords));
	}
	
	@RequestMapping(value="/getBillsByPage")
	public ResponseModel getBillsByPage(BillSearchCriteria billSearchCriteria,JSimplePageable simplePageable){
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
		JPage<BillRecord> billRecordsPage=billManagerService.getBillsByPage( billSearchCriteria,simplePageable);
		toBillRecordVOPage(billRecordsPage);
		return ResponseModel.newSuccess().setData(billRecordsPage);
	}
	
	@RequestMapping(value="/deleteBillById")
	public ResponseModel deleteBillById(String id){
		billManagerService.deleteBillById( id); 
		return ResponseModel.newSuccess();
	}

	@RequestMapping(value="/updateBill")
	public ResponseModel updateBill(BillRecordVO billRecordVO) throws JServiceException{
		billRecordVO.toBillRecord();
		billManagerService.updateBill( billRecordVO);
		return ResponseModel.newSuccess();
	}
	
	
	
	
	
	
	@RequestMapping(value="/saveGood")
	public ResponseModel saveGood(GoodRecordVO goodRecordVO) throws Exception {
		GoodRecord goodRecord=goodRecordVO.toGoodRecord();
		billManagerService.saveGood( goodRecord);
		return ResponseModel.newSuccess(goodRecord.getId());
	}
	
	@RequestMapping(value="/getGoodById")
	public ResponseModel getGoodById(String id) throws Exception {
		GoodRecord goodRecord= billManagerService.getGoodById(id);
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
	public ResponseModel getGoodsByUserName(String userName){
		List<GoodRecord> billRecords=billManagerService.getGoodsByUserName( userName);
		return ResponseModel.newSuccess().setData(toGoodRecordVOs(billRecords));
	}
	
	@RequestMapping(value="/getGoodsByPage")
	public ResponseModel getGoodsByPage(GoodSearchCriteria goodSearchCriteria,JSimplePageable simplePageable){
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
		JPage<GoodRecord> billRecordsPage=billManagerService.getGoodsByPage( goodSearchCriteria,simplePageable);
		toGoodRecordVOPage(billRecordsPage);
		return ResponseModel.newSuccess().setData(billRecordsPage);
	}
	
	@RequestMapping(value="/deleteGoodById")
	public ResponseModel deleteGoodById(String id){
		billManagerService.deleteGoodById( id); 
		return ResponseModel.newSuccess();
	}

	@RequestMapping(value="/updateGood")
	public ResponseModel updateGood(GoodRecordVO goodRecordVO) throws JServiceException{
		goodRecordVO.toGoodRecord();
		billManagerService.updateGood( goodRecordVO);
		return ResponseModel.newSuccess();
	}
	
}
