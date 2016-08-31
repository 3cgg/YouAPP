package com.youappcorp.project.resourcemanager.controller;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
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

import com.youappcorp.project.resourcemanager.model.Resource;
import com.youappcorp.project.resourcemanager.model.ResourceRecord;
import com.youappcorp.project.resourcemanager.service.ResourceManagerService;
import com.youappcorp.project.resourcemanager.vo.ResourceRecordVO;
import com.youappcorp.project.resourcemanager.vo.ResourceSearchCriteria;
import com.youappcorp.project.runtimeurl.model.RuntimeUrl;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlManagerService;

@Controller
@RequestMapping(value="/resourcemanager")
public class ResourceManagerController extends SimpleControllerSupport {
	
	@Autowired
	private ResourceManagerService resourceManagerService;
	
	private RuntimeUrlManagerService defaultRuntimeUrlService=JServiceHubDelegate.get().getService(this, RuntimeUrlManagerService.class);
	
	private ResourceRecordVO toResourceViewPage(ResourceRecord resourceRecord){
		return JObjectUtils.simpleCopy(resourceRecord, ResourceRecordVO.class);
	}
	
	private List<ResourceRecordVO> toResourceViewPage(List<ResourceRecord> resourceRecords ) {
		List<ResourceRecordVO> resourceRecordVOs=new ArrayList<ResourceRecordVO>();
		for(ResourceRecord resourceRecord:resourceRecords){
			resourceRecordVOs.add(toResourceViewPage(resourceRecord));
		}
		return resourceRecordVOs;
	}
	
	private void toResourceViewPage(JPage<ResourceRecord> resourceRecordPage ) {
		List<ResourceRecord> resourceRecords=resourceRecordPage.getContent();
		List<ResourceRecordVO> resourceRecordVOs = toResourceViewPage(resourceRecords);
		resourceRecordPage.setContent(resourceRecordVOs);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/refreshResource")
	public ResponseModel refreshResource(){
		for(RuntimeUrl runtimeUrl:defaultRuntimeUrlService.getAllRuntimeUrls()){
			Resource resource=new Resource();
			resource.setUrl(runtimeUrl.getUrl());
			resource.setDescription(runtimeUrl.getDesc());
			if(resourceManagerService.existsResourceByUrl( resource.getUrl())){
				continue;
			}
			resourceManagerService.saveResource( resource);
		}
		return ResponseModel.newSuccess();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getResourcesByPage")
	public ResponseModel getResourcesByPage(ResourceSearchCriteria resourceSearchCriteria,JSimplePageable simplePageable){
		JPage<ResourceRecord> resourceRecordPage= resourceManagerService.getResourcesByPage( resourceSearchCriteria, simplePageable);
		toResourceViewPage(resourceRecordPage);
		return ResponseModel.newSuccess().setData(resourceRecordPage);
	}
	
	
	@RequestMapping("/getResourceById")
	public ResponseModel getResourceById(String id){
		ResourceRecord resourceRecord=resourceManagerService.getResourceById( id);
		return ResponseModel.newSuccess(toResourceViewPage(resourceRecord));
	}
	
	@RequestMapping("/enableCache")
	public ResponseModel enableCache(String id){
		resourceManagerService.enableCache( id);
		return ResponseModel.newSuccess();
	}
	
	@RequestMapping("/disableCache")
	public ResponseModel disableCache(String id){
		resourceManagerService.disableCache( id);
		return ResponseModel.newSuccess();
	}
	
}
