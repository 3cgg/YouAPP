package com.youappcorp.project.sysparam.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.service.SysParamService;
import com.youappcorp.project.sysparam.vo.SysParamAddInVO;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;
import com.youappcorp.project.sysparam.vo.SysParamDetailOutVO;
import com.youappcorp.project.sysparam.vo.SysParamEditInVO;
import com.youappcorp.project.sysparam.vo.SysParamRecordOutVO;

/**
 * @author JIAZJ
 */
@Controller
@RequestMapping("/sysparam")
public class SysParamController extends SimpleControllerSupport {

	@Autowired
	private SysParamService sysParamService;
	
	@ResponseBody
	@RequestMapping("/saveSysParam")
	public ResponseModel saveSysParam(ServiceContext serviceContext, SysParamAddInVO sysParamAddInVO) throws Exception{
		// do something validation on the SysParam or nothing.
		SysParam sysParam=JObjectUtils.simpleCopy(sysParamAddInVO, SysParam.class);
		sysParamService.saveSysParam(serviceContext, sysParam);
		return ResponseModel.newSuccess(sysParam.getId());
	}
	
	@ResponseBody
	@RequestMapping("/updateSysParam")
	public ResponseModel updateSysParam(ServiceContext serviceContext, SysParamEditInVO sysParamEditInVO) throws Exception{
		// do something validation on the SysParam or nothing.
		SysParam sysParam=JObjectUtils.simpleCopy(sysParamEditInVO, SysParam.class);
		sysParamService.updateSysParam(serviceContext, sysParam);
		return ResponseModel.newSuccess(sysParam.getId());
	}
	
	@ResponseBody
	@RequestMapping("/getSysParamById")
	public ResponseModel getSysParamById(ServiceContext serviceContext, String id) throws Exception{
		// do something validation on the SysParam or nothing.
		SysParam sysParam=sysParamService.getSysParamById(serviceContext, id);
		SysParamDetailOutVO sysParamDetailOutVO=null;
		if(sysParam!=null){
			sysParamDetailOutVO=JObjectUtils.simpleCopy(sysParam, SysParamDetailOutVO.class);
		}
		return ResponseModel.newSuccess(sysParamDetailOutVO);
	}
	
	
	@ResponseBody
	@RequestMapping("/deleteSysParamById")
	public ResponseModel deleteSysParamById(ServiceContext serviceContext, String id) throws Exception{
		// do something validation on the SysParam or nothing.
		sysParamService.deleteSysParamById(serviceContext, id);
		return ResponseModel.newSuccess(true);
	}
	
	
	@ResponseBody
	@RequestMapping("/getSysParamsByPage")
	public ResponseModel getSysParamsByPage(ServiceContext serviceContext, SysParamCriteriaInVO carCriteriaInVO,JSimplePageable simplePageable ) throws Exception{
		// do something validation on the SysParam or nothing.
		JPage<SysParam> page=sysParamService.getSysParams(serviceContext, carCriteriaInVO,simplePageable);
		List<SysParam> content=page.getContent();
		List<SysParamRecordOutVO> outContent=new ArrayList<SysParamRecordOutVO>();
		for(SysParam sysParam:content){
			SysParamRecordOutVO sysParamRecordOutVO= JObjectUtils.simpleCopy(sysParam, SysParamRecordOutVO.class);
			outContent.add(sysParamRecordOutVO);
		}
		page.setContent(outContent);
		return ResponseModel.newSuccess(page);
	}
	
	
	
	
}
