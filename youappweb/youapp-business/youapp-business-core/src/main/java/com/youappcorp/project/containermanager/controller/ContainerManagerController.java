package com.youappcorp.project.containermanager.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerSupport;

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
	public ResponseModel getAllAppMetas(ServiceContext serviceContext){
		List<AppMeta> appMetas= containerManagerService.getAllAppMetas(serviceContext);
		return ResponseModel.newSuccess().setData(appMetas);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllURLMappingMetasByAppId")
	public ResponseModel getAllURLMappingMetasByAppId(ServiceContext serviceContext,String appId){
		List<URLMappingMeta> urlMappingMetas= containerManagerService.getAllURLMappingMetasByAppId(serviceContext, appId);
		return ResponseModel.newSuccess().setData(urlMappingMetas);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getAllURLMappingMetasByAppConfig")
	public ResponseModel getAllURLMappingMetasByAppConfig(ServiceContext serviceContext,String appName,String appCompName,String appVersion){
		List<URLMappingMeta> urlMappingMetas= containerManagerService.getAllURLMappingMetasByAppConfig(serviceContext, appName, appCompName, appVersion);
		return ResponseModel.newSuccess().setData(urlMappingMetas);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppConfig")
	public ResponseModel getAppMetaByAppConfig(ServiceContext serviceContext,String appName,String appCompName,String appVersion){
		AppMeta appMeta= containerManagerService.getAPPMetaByConfig(serviceContext, appName, appCompName, appVersion);
		return ResponseModel.newSuccess().setData(appMeta);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppId")
	public ResponseModel getAppMetaByAppId(ServiceContext serviceContext,String appId){
		AppMeta appMeta= containerManagerService.getAPPMetaByAppId(serviceContext, appId);
		return ResponseModel.newSuccess().setData(appMeta);
	}
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetaById")
	public ResponseModel getURLMappingMetaById(ServiceContext serviceContext,String id){
		URLMappingMeta urlMappingMeta= containerManagerService.getURLMappingMetaById(serviceContext, id);
		return ResponseModel.newSuccess().setData(urlMappingMeta);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllURLMappingMetasByPage")
	public ResponseModel getAllURLMappingMetasByPage(ServiceContext serviceContext,URLMappingMetaCriteria urlMappingMetaCriteria){
		JPage<URLMappingMeta>  urlMappingMetaPage= containerManagerService.getAllURLMappingMetasByPage(serviceContext, urlMappingMetaCriteria);
		return ResponseModel.newSuccess().setData(urlMappingMetaPage);
	}
}
