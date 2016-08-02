package com.youappcorp.project.runtimeurl.impl;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.support._package.JDefaultParamMeta;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.data.common.MethodParamMeta;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.webcomp.web.youappmvc.container.ContainerMappingMeta;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.youappcorp.project.runtimeurl.model.MockInfo;
import com.youappcorp.project.runtimeurl.model.RuntimeUrl;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlManagerService;

public class DefaultRuntimeUrlManagerService
extends JServiceFactorySupport<RuntimeUrlManagerService>
implements RuntimeUrlManagerService {

	private HttpInvokeContainerDelegateService httpInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	@Override
	protected RuntimeUrlManagerService doGetService() {
		return this;
	}
	/**
	 * KEY IS URL
	 */
	private Map<String, RuntimeUrl> runtimeUrls=new LinkedHashMap<String, RuntimeUrl>();
	
	/**
	 * KEY IS ID
	 */
	private Map<String, RuntimeUrl> backUrls=new LinkedHashMap<String, RuntimeUrl>();
	
	
	private Object sync=new Object();
	
	private boolean ready(){
		if(runtimeUrls.size()>0){
			return true;
		}
		
		synchronized (sync) {
			if(runtimeUrls.size()>0){
				return true;
			}
			runtimeUrls.clear();
			backUrls.clear();
			//TODO ready to do 
			List<ContainerMappingMeta> containerMappingMetas= httpInvokeContainerDelegateService.getRuntimeAllMappingMetas();
			for(ContainerMappingMeta containerMappingMeta:containerMappingMetas){
				Collection<MappingMeta> mappingMetas = containerMappingMeta.getMappingMetas();
				for(MappingMeta mappingMeta:mappingMetas){
					RuntimeUrl runtimeUrl=new RuntimeUrl();
					runtimeUrl.setUrl(mappingMeta.getPath());
					runtimeUrl.setName(mappingMeta.getMethodName());
					runtimeUrl.setDesc("");
					runtimeUrl.setId(JUniqueUtils.unique());
					runtimeUrl.setContainerUnique(containerMappingMeta.getUnique());
					
					JDefaultMethodMeta methodMeta=new JDefaultMethodMeta();
					methodMeta.setMethodName(mappingMeta.getMethodName());
					methodMeta.setAnnotations(mappingMeta.getAnnotations());
					MethodParamMeta[] methodParamMetas= mappingMeta.getMethodParams();
					JDefaultParamMeta[] defaultParamMetas=new JDefaultParamMeta[methodParamMetas.length];
					int i=0;
					for(MethodParamMeta methodParamMeta:methodParamMetas){
						JDefaultParamMeta defaultParamMeta=new JDefaultParamMeta();
						defaultParamMeta.setName(methodParamMeta.getName());
						defaultParamMeta.setType(methodParamMeta.getType());
						defaultParamMeta.setAnnotations(methodParamMeta.getAnnotations());
						defaultParamMeta.setIndex(methodParamMeta.getIndex());
						defaultParamMetas[i++]=defaultParamMeta;
					}
					methodMeta.setParamMetas(defaultParamMetas); 
					runtimeUrl.setMethodMeta(methodMeta);
					
					//mock info
					MockInfo mockInfo=new MockInfo();
					runtimeUrl.setMockInfo(mockInfo);
					
					runtimeUrls.put(runtimeUrl.getUrl(), runtimeUrl);
					backUrls.put(runtimeUrl.getId(), runtimeUrl);
				}
			}
			return true;
		}
	}
	
	@Override
	public void updateMockState( String url,
			boolean mock) {
		ready();
		RuntimeUrl runtimeUrl=getRuntimeUrlByUrl(url);
		runtimeUrl.getMockInfo().setMock(mock);
	}

	@Override
	public RuntimeUrl getRuntimeUrlByUrl(
			String url) {
		ready();
		return runtimeUrls.get(url);
	}
	
	@Override
	public RuntimeUrl getRuntimeUrlById(
			String id) {
		ready();
		return backUrls.get(id);
	}

	@Override
	public boolean exists( String url) {
		ready();
		return runtimeUrls.containsKey(url);
	}

	@Override
	public boolean isMock( String url) {
		ready();
		return runtimeUrls.get(url).getMockInfo().isMock();
	}

	@Override
	public Collection<RuntimeUrl> getAllRuntimeUrls() {
		ready();
		return Collections.unmodifiableCollection(runtimeUrls.values());
	}

}
