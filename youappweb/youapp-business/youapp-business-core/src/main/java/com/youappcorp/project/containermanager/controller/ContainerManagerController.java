package com.youappcorp.project.containermanager.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.model.URLMappingMetaCriteria;
import com.youappcorp.project.containermanager.service.ContainerManagerService;

@Controller
@RequestMapping(value="/containermanager")
public class ContainerManagerController extends ControllerSupport {

	@Autowired
	private ContainerManagerService containerManagerService;
	
	@ResponseBody
	@RequestMapping(value="/getAllAppMetas")
	public ResponseModel getAllAppMetas(){
		List<AppMeta> appMetas= containerManagerService.getAllAppMetas(getServiceContext());
		return ResponseModel.newSuccess().setData(appMetas);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllURLMappingMetasByAppId")
	public ResponseModel getAllURLMappingMetasByAppId(String appId){
		List<URLMappingMeta> urlMappingMetas= containerManagerService.getAllURLMappingMetasByAppId(getServiceContext(), appId);
		return ResponseModel.newSuccess().setData(urlMappingMetas);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getAllURLMappingMetasByAppConfig")
	public ResponseModel getAllURLMappingMetasByAppConfig(String appName,String appCompName,String appVersion){
		List<URLMappingMeta> urlMappingMetas= containerManagerService.getAllURLMappingMetasByAppConfig(getServiceContext(), appName, appCompName, appVersion);
		return ResponseModel.newSuccess().setData(urlMappingMetas);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppConfig")
	public ResponseModel getAppMetaByAppConfig(String appName,String appCompName,String appVersion){
		AppMeta appMeta= containerManagerService.getAPPMetaByConfig(getServiceContext(), appName, appCompName, appVersion);
		return ResponseModel.newSuccess().setData(appMeta);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppId")
	public ResponseModel getAppMetaByAppId(String appId){
		AppMeta appMeta= containerManagerService.getAPPMetaByAppId(getServiceContext(), appId);
		return ResponseModel.newSuccess().setData(appMeta);
	}
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetaById")
	public ResponseModel getURLMappingMetaById(String id){
		URLMappingMeta urlMappingMeta= containerManagerService.getURLMappingMetaById(getServiceContext(), id);
		return ResponseModel.newSuccess().setData(urlMappingMeta);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllURLMappingMetasByPage")
	public ResponseModel getAllURLMappingMetasByPage(URLMappingMetaCriteria urlMappingMetaCriteria){
		JPage<URLMappingMeta>  urlMappingMetaPage= containerManagerService.getAllURLMappingMetasByPage(getServiceContext(), urlMappingMetaCriteria);
		return ResponseModel.newSuccess().setData(urlMappingMetaPage);
	}
}
