package com.youappcorp.project.runtimeurl.service;

import com.youappcorp.project.runtimeurl.model.RuntimeUrl;
import com.youappcorp.project.runtimeurl.vo.RuntimeUrlCriteriaInVO;

import me.bunny.kernel.jave.model.JPage;
import me.bunny.kernel.jave.model.JSimplePageable;

public interface RuntimeUrlService {
	
	void updateMockState(String url,boolean mock);
	
	RuntimeUrl getRuntimeUrlByUrl(String url);
	
	RuntimeUrl getRuntimeUrlById(String id);
	
	JPage<RuntimeUrl> getRuntimeUrlsByPage(RuntimeUrlCriteriaInVO runtimeUrlCriteriaInVO, JSimplePageable simplePageable);
	
	boolean exists(String url);
	
	boolean isMock(String url);
	
}
