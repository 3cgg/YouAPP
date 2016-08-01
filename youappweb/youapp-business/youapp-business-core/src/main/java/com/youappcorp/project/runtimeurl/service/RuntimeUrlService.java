package com.youappcorp.project.runtimeurl.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;

import com.youappcorp.project.runtimeurl.model.RuntimeUrl;
import com.youappcorp.project.runtimeurl.vo.RuntimeUrlCriteriaInVO;

public interface RuntimeUrlService {
	
	void updateMockState(ServiceContext serviceContext,String url,boolean mock);
	
	RuntimeUrl getRuntimeUrlByUrl(ServiceContext serviceContext,String url);
	
	RuntimeUrl getRuntimeUrlById(ServiceContext serviceContext,String id);
	
	JPage<RuntimeUrl> getRuntimeUrlsByPage(ServiceContext serviceContext,RuntimeUrlCriteriaInVO runtimeUrlCriteriaInVO, JSimplePageable simplePageable);
	
	boolean exists(ServiceContext serviceContext,String url);
	
	boolean isMock(ServiceContext serviceContext,String url);
	
}
