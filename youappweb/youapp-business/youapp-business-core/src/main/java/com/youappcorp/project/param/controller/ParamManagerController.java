package com.youappcorp.project.param.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerSupport;

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
	public ResponseModel getParamTypesByPage(ParamCriteria paramCriteria){
		JPage<ParamType> paramTypesPage= paramService.getAllParamTypes(getServiceContext(),paramCriteria);
		return ResponseModel.newSuccess().setData(paramTypesPage);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByPage")
	public ResponseModel getParamCodesByPage(ParamCriteria paramCriteria){
		JPage<ParamCode> paramCodesPage= paramService.getAllParamCodes(getServiceContext(),paramCriteria);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByTypePage")
	public ResponseModel getParamCodesByTypePage(ParamCriteria paramCriteria){
		JPage<ParamCode> paramCodesPage= paramService.getAllParamCodesByType(getServiceContext(),paramCriteria);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}
	
}
