package com.youappcorp.project.runtimeurl.impl;

import j.jave.platform.data.common.MethodParamMeta;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.webcomp.web.youappmvc.container.ContainerMappingMeta;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.async.JAsyncExecutor;
import me.bunny.kernel.jave.async.JAsyncTaskExecutingService;
import me.bunny.kernel.jave.support._package.JDefaultMethodMeta;
import me.bunny.kernel.jave.support._package.JDefaultParamMeta;
import me.bunny.kernel.jave.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.youappcorp.project.runtimeurl.model.MockInfo;
import com.youappcorp.project.runtimeurl.model.RuntimeUrl;
import com.youappcorp.project.runtimeurl.model.RuntimeUrlSerializable;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlManagerService;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlSerializeService;

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
	
	private RuntimeUrlSerializeService runtimeUrlSerializeService=new SimpleRuntimeUrlSerializeService(); 
	
	private JAsyncTaskExecutingService asyncTaskExecutingService=JServiceHubDelegate.get().getService(this, JAsyncTaskExecutingService.class);
	
	private Object sync=new Object();
	
	public void cleanup(){
		synchronized (sync) {
			runtimeUrls.clear();
			backUrls.clear();
		}
	}
	
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
			Map<String, RuntimeUrlSerializable> seria=new HashMap<String, RuntimeUrlSerializable>();
			try {
				Collection<RuntimeUrlSerializable> serializeRuntimeUrls= runtimeUrlSerializeService.read();
				for(RuntimeUrlSerializable runtimeUrl:serializeRuntimeUrls){
					seria.put(runtimeUrl.getUrl(), runtimeUrl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//TODO ready to do 
			List<ContainerMappingMeta> containerMappingMetas= httpInvokeContainerDelegateService.getRuntimeAllMappingMetas();
			for(ContainerMappingMeta containerMappingMeta:containerMappingMetas){
				Collection<MappingMeta> mappingMetas = containerMappingMeta.getMappingMetas();
				for(MappingMeta mappingMeta:mappingMetas){
					String url=mappingMeta.getPath();
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
					RuntimeUrlSerializable runtimeUrlSerializable=null;
					if((runtimeUrlSerializable=seria.get(url))!=null){
						mockInfo.setMock(runtimeUrlSerializable.isMock());
					}
					runtimeUrls.put(runtimeUrl.getUrl(), runtimeUrl);
					backUrls.put(runtimeUrl.getId(), runtimeUrl);
				}
			}
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void serializeAsync(){
		asyncTaskExecutingService.addAsyncTask(new JAsyncExecutor() {
			@Override
			public Object execute(Object data) throws Exception {
				try {
					List<RuntimeUrlSerializable> runtimeUrlSerializables=new ArrayList<RuntimeUrlSerializable>();
					for(RuntimeUrl runtimeUrl:runtimeUrls.values()){
						RuntimeUrlSerializable runtimeUrlSerializable=new RuntimeUrlSerializable();
						runtimeUrlSerializable.setMock(runtimeUrl.getMockInfo().isMock());
						runtimeUrlSerializable.setUrl(runtimeUrl.getUrl());
						runtimeUrlSerializables.add(runtimeUrlSerializable);
					}
					runtimeUrlSerializeService.serialize(runtimeUrlSerializables);
				} catch (Exception e) {
					LOGGER.debug(e.getMessage(), e);
				}
				return true;
			}
			
		});
	}
	
	
	@Override
	public void updateMockState( String url,
			boolean mock) {
		ready();
		RuntimeUrl runtimeUrl=getRuntimeUrlByUrl(url);
		runtimeUrl.getMockInfo().setMock(mock);
		serializeAsync();
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
