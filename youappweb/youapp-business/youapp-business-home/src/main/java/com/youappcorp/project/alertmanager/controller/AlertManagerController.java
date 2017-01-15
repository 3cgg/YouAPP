package com.youappcorp.project.alertmanager.controller;

import me.bunny.app._c._web.web.model.ResponseModel;
import me.bunny.app._c._web.web.youappmvc.controller.SimpleControllerSupport;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.utils.JObjectUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.alertmanager.model.AlertItem;
import com.youappcorp.project.alertmanager.model.AlertItemRecord;
import com.youappcorp.project.alertmanager.vo.AlertItemRecordVO;
import com.youappcorp.project.alertmanager.vo.AlertItemCriteria;
import com.youappcorp.project.alertmanager.service.AlertManagerService;
import com.youappcorp.project.alertmanager.model.AlertRecord;
import com.youappcorp.project.alertmanager.model.AlertRecordRecord;
import com.youappcorp.project.alertmanager.vo.AlertRecordRecordVO;
import com.youappcorp.project.alertmanager.vo.AlertRecordCriteria;
import com.youappcorp.project.alertmanager.service.AlertManagerService;


@Controller
@RequestMapping("/alertmanager")
public class AlertManagerController extends SimpleControllerSupport {

	@Autowired
	private AlertManagerService alertManagerService;
	

	/**
	 * save
	 */
	@ResponseBody
	@RequestMapping("/saveAlertItem")
	public ResponseModel saveAlertItem (AlertItemRecordVO alertItemRecordVO) throws Exception {
		// do something validation on the alertItemRecordVO.
		alertManagerService.saveAlertItem( alertItemRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * update
	 */
	@ResponseBody
	@RequestMapping("/updateAlertItem")
	public ResponseModel updateAlertItem (AlertItemRecordVO alertItemRecordVO) throws Exception {
		// do something validation on the alertItemRecordVO.
		alertManagerService.updateAlertItem( alertItemRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteAlertItem")
	public ResponseModel deleteAlertItem (AlertItemRecordVO alertItemRecordVO) throws Exception {
		// do something validation on the alertItemRecordVO.
		alertManagerService.deleteAlertItem( alertItemRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteAlertItemById")
	public ResponseModel deleteAlertItemById (String id) throws Exception {
		// do something validation on the alertItemRecordVO.
		alertManagerService.deleteAlertItemById( id);
		return ResponseModel.newSuccess(true);
	}
	
	
	private AlertItemRecordVO toAlertItemRecordVO(AlertItemRecord alertItemRecord) {
		return JObjectUtils.simpleCopy(alertItemRecord, AlertItemRecordVO.class);
	}
	
	private List<AlertItemRecordVO> toAlertItemRecordVOs(List<AlertItemRecord> alertItemRecords) {
		List<AlertItemRecordVO> alertItemRecordVOs=new ArrayList<AlertItemRecordVO>();
		for(AlertItemRecord alertItemRecord:alertItemRecords){
			alertItemRecordVOs.add(toAlertItemRecordVO(alertItemRecord));
		}
		return alertItemRecordVOs;
	}
	
	private void toAlertItemRecordVOPage(JPage<AlertItemRecord> alertItemRecordsPage) {
		alertItemRecordsPage.setContent(toAlertItemRecordVOs(alertItemRecordsPage.getContent()));
	}
	
	
	/**
	 * get
	 */
	@ResponseBody
	@RequestMapping("/getAlertItemById")
	public ResponseModel getAlertItemById (String id) throws Exception {
		AlertItemRecord alertItemRecord= alertManagerService.getAlertItemById( id);
		return ResponseModel.newSuccess().setData(toAlertItemRecordVO(alertItemRecord));
	}
	
	/**
	 * page...
	 */
	@ResponseBody
	@RequestMapping("/getAlertItemsByPage")
	public ResponseModel getAlertItemsByPage(AlertItemCriteria alertItemCriteria, JSimplePageable simplePageable) throws Exception {
		JPage<AlertItemRecord> alertItemRecordsPage=alertManagerService.getAlertItemsByPage( alertItemCriteria,simplePageable);
		toAlertItemRecordVOPage(alertItemRecordsPage);
		return ResponseModel.newSuccess().setData(alertItemRecordsPage);
	}

	/**
	 * save
	 */
	@ResponseBody
	@RequestMapping("/saveAlertRecord")
	public ResponseModel saveAlertRecord (AlertRecordRecordVO alertRecordRecordVO) throws Exception {
		// do something validation on the alertRecordRecordVO.
		alertManagerService.saveAlertRecord( alertRecordRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * update
	 */
	@ResponseBody
	@RequestMapping("/updateAlertRecord")
	public ResponseModel updateAlertRecord (AlertRecordRecordVO alertRecordRecordVO) throws Exception {
		// do something validation on the alertRecordRecordVO.
		alertManagerService.updateAlertRecord( alertRecordRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteAlertRecord")
	public ResponseModel deleteAlertRecord (AlertRecordRecordVO alertRecordRecordVO) throws Exception {
		// do something validation on the alertRecordRecordVO.
		alertManagerService.deleteAlertRecord( alertRecordRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteAlertRecordById")
	public ResponseModel deleteAlertRecordById (String id) throws Exception {
		// do something validation on the alertRecordRecordVO.
		alertManagerService.deleteAlertRecordById( id);
		return ResponseModel.newSuccess(true);
	}
	
	
	private AlertRecordRecordVO toAlertRecordRecordVO(AlertRecordRecord alertRecordRecord) {
		return JObjectUtils.simpleCopy(alertRecordRecord, AlertRecordRecordVO.class);
	}
	
	private List<AlertRecordRecordVO> toAlertRecordRecordVOs(List<AlertRecordRecord> alertRecordRecords) {
		List<AlertRecordRecordVO> alertRecordRecordVOs=new ArrayList<AlertRecordRecordVO>();
		for(AlertRecordRecord alertRecordRecord:alertRecordRecords){
			alertRecordRecordVOs.add(toAlertRecordRecordVO(alertRecordRecord));
		}
		return alertRecordRecordVOs;
	}
	
	private void toAlertRecordRecordVOPage(JPage<AlertRecordRecord> alertRecordRecordsPage) {
		alertRecordRecordsPage.setContent(toAlertRecordRecordVOs(alertRecordRecordsPage.getContent()));
	}
	
	
	/**
	 * get
	 */
	@ResponseBody
	@RequestMapping("/getAlertRecordById")
	public ResponseModel getAlertRecordById (String id) throws Exception {
		AlertRecordRecord alertRecordRecord= alertManagerService.getAlertRecordById( id);
		return ResponseModel.newSuccess().setData(toAlertRecordRecordVO(alertRecordRecord));
	}
	
	/**
	 * page...
	 */
	@ResponseBody
	@RequestMapping("/getAlertRecordsByPage")
	public ResponseModel getAlertRecordsByPage(AlertRecordCriteria alertRecordCriteria, JSimplePageable simplePageable) throws Exception {
		JPage<AlertRecordRecord> alertRecordRecordsPage=alertManagerService.getAlertRecordsByPage( alertRecordCriteria,simplePageable);
		toAlertRecordRecordVOPage(alertRecordRecordsPage);
		return ResponseModel.newSuccess().setData(alertRecordRecordsPage);
	}

	
}
