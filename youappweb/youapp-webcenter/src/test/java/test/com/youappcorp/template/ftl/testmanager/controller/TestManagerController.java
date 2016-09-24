package test.com.youappcorp.template.ftl.testmanager.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import test.com.youappcorp.template.ftl.testmanager.model.ParamCode;
import test.com.youappcorp.template.ftl.testmanager.model.ParamCodeRecord;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamCodeRecordVO;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamCodeCriteria;
import test.com.youappcorp.template.ftl.testmanager.service.TestManagerService;
import test.com.youappcorp.template.ftl.testmanager.model.ParamType;
import test.com.youappcorp.template.ftl.testmanager.model.ParamTypeRecord;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamTypeRecordVO;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamTypeCriteria;
import test.com.youappcorp.template.ftl.testmanager.service.TestManagerService;


@Controller
@RequestMapping("/testmanager")
public class TestManagerController extends SimpleControllerSupport {

	@Autowired
	private TestManagerService testManagerService;
	

	/**
	 * save
	 */
	@ResponseBody
	@RequestMapping("/saveParamCode")
	public ResponseModel saveParamCode (ParamCodeRecordVO paramCodeRecordVO) throws Exception {
		// do something validation on the paramCodeRecordVO.
		testManagerService.saveParamCode( paramCodeRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * update
	 */
	@ResponseBody
	@RequestMapping("/updateParamCode")
	public ResponseModel updateParamCode (ParamCodeRecordVO paramCodeRecordVO) throws Exception {
		// do something validation on the paramCodeRecordVO.
		testManagerService.updateParamCode( paramCodeRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteParamCode")
	public ResponseModel deleteParamCode (ParamCodeRecordVO paramCodeRecordVO) throws Exception {
		// do something validation on the paramCodeRecordVO.
		testManagerService.deleteParamCode( paramCodeRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteParamCodeById")
	public ResponseModel deleteParamCodeById (String id) throws Exception {
		// do something validation on the paramCodeRecordVO.
		testManagerService.deleteParamCodeById( id);
		return ResponseModel.newSuccess(true);
	}
	
	
	private ParamCodeRecordVO toParamCodeRecordVO(ParamCodeRecord paramCodeRecord) {
		return JObjectUtils.simpleCopy(paramCodeRecord, ParamCodeRecordVO.class);
	}
	
	private List<ParamCodeRecordVO> toParamCodeRecordVOs(List<ParamCodeRecord> paramCodeRecords) {
		List<ParamCodeRecordVO> paramCodeRecordVOs=new ArrayList<ParamCodeRecordVO>();
		for(ParamCodeRecord paramCodeRecord:paramCodeRecords){
			paramCodeRecordVOs.add(toParamCodeRecordVO(paramCodeRecord));
		}
		return paramCodeRecordVOs;
	}
	
	private void toParamCodeRecordVOPage(JPage<ParamCodeRecord> paramCodeRecordsPage) {
		paramCodeRecordsPage.setContent(toParamCodeRecordVOs(paramCodeRecordsPage.getContent()));
	}
	
	
	/**
	 * get
	 */
	@ResponseBody
	@RequestMapping("/getParamCodeById")
	public ResponseModel getParamCodeById (String id) throws Exception {
		ParamCodeRecord paramCodeRecord= testManagerService.getParamCodeById( id);
		return ResponseModel.newSuccess().setData(toParamCodeRecordVO(paramCodeRecord));
	}
	
	/**
	 * page...
	 */
	@ResponseBody
	@RequestMapping("/getParamCodesByPage")
	public ResponseModel getParamCodesByPage(ParamCodeCriteria paramCodeCriteria, JSimplePageable simplePageable) throws Exception {
		JPage<ParamCodeRecord> paramCodeRecordsPage=testManagerService.getParamCodesByPage( paramCodeCriteria,simplePageable);
		toParamCodeRecordVOPage(paramCodeRecordsPage);
		return ResponseModel.newSuccess().setData(paramCodeRecordsPage);
	}

	/**
	 * save
	 */
	@ResponseBody
	@RequestMapping("/saveParamType")
	public ResponseModel saveParamType (ParamTypeRecordVO paramTypeRecordVO) throws Exception {
		// do something validation on the paramTypeRecordVO.
		testManagerService.saveParamType( paramTypeRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * update
	 */
	@ResponseBody
	@RequestMapping("/updateParamType")
	public ResponseModel updateParamType (ParamTypeRecordVO paramTypeRecordVO) throws Exception {
		// do something validation on the paramTypeRecordVO.
		testManagerService.updateParamType( paramTypeRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteParamType")
	public ResponseModel deleteParamType (ParamTypeRecordVO paramTypeRecordVO) throws Exception {
		// do something validation on the paramTypeRecordVO.
		testManagerService.deleteParamType( paramTypeRecordVO);
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/deleteParamTypeById")
	public ResponseModel deleteParamTypeById (String id) throws Exception {
		// do something validation on the paramTypeRecordVO.
		testManagerService.deleteParamTypeById( id);
		return ResponseModel.newSuccess(true);
	}
	
	
	private ParamTypeRecordVO toParamTypeRecordVO(ParamTypeRecord paramTypeRecord) {
		return JObjectUtils.simpleCopy(paramTypeRecord, ParamTypeRecordVO.class);
	}
	
	private List<ParamTypeRecordVO> toParamTypeRecordVOs(List<ParamTypeRecord> paramTypeRecords) {
		List<ParamTypeRecordVO> paramTypeRecordVOs=new ArrayList<ParamTypeRecordVO>();
		for(ParamTypeRecord paramTypeRecord:paramTypeRecords){
			paramTypeRecordVOs.add(toParamTypeRecordVO(paramTypeRecord));
		}
		return paramTypeRecordVOs;
	}
	
	private void toParamTypeRecordVOPage(JPage<ParamTypeRecord> paramTypeRecordsPage) {
		paramTypeRecordsPage.setContent(toParamTypeRecordVOs(paramTypeRecordsPage.getContent()));
	}
	
	
	/**
	 * get
	 */
	@ResponseBody
	@RequestMapping("/getParamTypeById")
	public ResponseModel getParamTypeById (String id) throws Exception {
		ParamTypeRecord paramTypeRecord= testManagerService.getParamTypeById( id);
		return ResponseModel.newSuccess().setData(toParamTypeRecordVO(paramTypeRecord));
	}
	
	/**
	 * page...
	 */
	@ResponseBody
	@RequestMapping("/getParamTypesByPage")
	public ResponseModel getParamTypesByPage(ParamTypeCriteria paramTypeCriteria, JSimplePageable simplePageable) throws Exception {
		JPage<ParamTypeRecord> paramTypeRecordsPage=testManagerService.getParamTypesByPage( paramTypeCriteria,simplePageable);
		toParamTypeRecordVOPage(paramTypeRecordsPage);
		return ResponseModel.newSuccess().setData(paramTypeRecordsPage);
	}

	
}
