package com.youappcorp.project.runtimeurl.impl;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.youappcorp.project.runtimeurl.model.RuntimeUrl;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlManagerService;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlService;
import com.youappcorp.project.runtimeurl.vo.RuntimeUrlCriteriaInVO;

@Service
public class RuntimeUrlServiceImpl  extends ServiceSupport  implements RuntimeUrlService {

	private RuntimeUrlManagerService defaultRuntimeUrlService=JServiceHubDelegate.get().getService(this, RuntimeUrlManagerService.class);
			
	@Override
	public void updateMockState( String url,
			boolean mock) {
		defaultRuntimeUrlService.updateMockState(url, mock);
	}

	@Override
	public RuntimeUrl getRuntimeUrlById(
			String id) {
		return defaultRuntimeUrlService.getRuntimeUrlById(id);
	}
	
	@Override
	public RuntimeUrl getRuntimeUrlByUrl(
			String url) {
		return defaultRuntimeUrlService.getRuntimeUrlByUrl(url);
	}

	@Override
	public JPage<RuntimeUrl> getRuntimeUrlsByPage(
			RuntimeUrlCriteriaInVO runtimeUrlCriteriaInVO,
			JSimplePageable simplePageable) {
		List<RuntimeUrl> urls=new ArrayList<RuntimeUrl>(32); 
		for(RuntimeUrl runtimeUrl:defaultRuntimeUrlService.getAllRuntimeUrls()){
			if(runtimeUrl.getUrl().contains(runtimeUrlCriteriaInVO.getUrl())
					&&runtimeUrl.getName().contains(runtimeUrlCriteriaInVO.getName())
					&&runtimeUrl.getDesc().contains(runtimeUrlCriteriaInVO.getDesc())
					){
				urls.add(runtimeUrl);
			}
		}
		int pageNumber=simplePageable.getPageNumber();
		int pageSize=simplePageable.getPageSize();
		int start=pageNumber*pageSize;
		int end=(start+pageSize)>urls.size()?urls.size():(start+pageSize);
		
		JPageImpl<RuntimeUrl> page=new JPageImpl<RuntimeUrl>();
		page.setTotalPageNumber(urls.size()/pageSize);
		page.setTotalRecordNumber(urls.size());
		page.setContent(urls.subList(start, end));
		page.setPageable(simplePageable);
		return page;
	}

	@Override
	public boolean exists( String url) {
		return defaultRuntimeUrlService.exists(url);
	}

	@Override
	public boolean isMock( String url) {
		return defaultRuntimeUrlService.isMock(url);
	}

}
