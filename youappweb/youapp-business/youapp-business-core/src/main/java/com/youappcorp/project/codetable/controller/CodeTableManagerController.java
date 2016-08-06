package com.youappcorp.project.codetable.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.codetable.model.ParamCode;
import com.youappcorp.project.codetable.model.ParamType;
import com.youappcorp.project.codetable.service.CodeTableService;
import com.youappcorp.project.codetable.vo.ParamCriteriaInVO;

@Controller
@RequestMapping(value="/codetablemanager")
public class CodeTableManagerController extends SimpleControllerSupport {
	
	@Autowired
	private CodeTableService paramService;
	
	@ResponseBody
	@RequestMapping(value="/getParamTypesByPage")
	public ResponseModel getParamTypesByPage(ServiceContext serviceContext,ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable){
		JPage<ParamType> paramTypesPage= paramService.getAllParamTypes(serviceContext,paramCriteria,simplePageable);
		return ResponseModel.newSuccess().setData(paramTypesPage);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByPage")
	public ResponseModel getParamCodesByPage(ServiceContext serviceContext,ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable){
		JPage<ParamCode> paramCodesPage= paramService.getAllParamCodes(serviceContext,paramCriteria,simplePageable);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByTypePage")
	public ResponseModel getParamCodesByTypePage(ServiceContext serviceContext,ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable){
		JPage<ParamCode> paramCodesPage= paramService.getAllParamCodesByType(serviceContext,paramCriteria,simplePageable);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}
	
}
