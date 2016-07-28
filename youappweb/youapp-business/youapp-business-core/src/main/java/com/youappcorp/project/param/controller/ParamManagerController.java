package com.youappcorp.project.param.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.param.model.ParamCode;
import com.youappcorp.project.param.model.ParamCriteria;
import com.youappcorp.project.param.model.ParamType;
import com.youappcorp.project.param.service.ParamService;

@Controller
@RequestMapping(value="/parammanager")
public class ParamManagerController extends ControllerSupport {
	
	@Autowired
	private ParamService paramService;
	
	@ResponseBody
	@RequestMapping(value="/getParamTypesByPage")
	public ResponseModel getParamTypesByPage(ServiceContext serviceContext,ParamCriteria paramCriteria,JSimplePageable simplePageable){
		JPage<ParamType> paramTypesPage= paramService.getAllParamTypes(serviceContext,paramCriteria);
		return ResponseModel.newSuccess().setData(paramTypesPage);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByPage")
	public ResponseModel getParamCodesByPage(ServiceContext serviceContext,ParamCriteria paramCriteria,JSimplePageable simplePageable){
		JPage<ParamCode> paramCodesPage= paramService.getAllParamCodes(serviceContext,paramCriteria);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByTypePage")
	public ResponseModel getParamCodesByTypePage(ServiceContext serviceContext,ParamCriteria paramCriteria,JSimplePageable simplePageable){
		JPage<ParamCode> paramCodesPage= paramService.getAllParamCodesByType(serviceContext,paramCriteria);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}
	
}
