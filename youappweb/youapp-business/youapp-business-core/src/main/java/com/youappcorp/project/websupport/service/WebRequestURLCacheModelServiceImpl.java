package com.youappcorp.project.websupport.service;

import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheModel;
import j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheModelService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.resource.model.ResourceExtend;
import com.youappcorp.project.resource.service.ResourceExtendService;
import com.youappcorp.project.websupport.model.WebRequestURLCacheModelImpl;

@Service
public class WebRequestURLCacheModelServiceImpl implements
		WebRequestURLCacheModelService {

	@Autowired
	private ResourceExtendService resourceExtendService;
	
	@Override
	public List<? extends WebRequestURLCacheModel> getResourceCacheModels() {
		List<WebRequestURLCacheModel> cacheModels=new ArrayList<WebRequestURLCacheModel>();
		ServiceContext serviceContext=DefaultServiceContext.getDefaultServiceContext();
		List<ResourceExtend> resourceExtends= resourceExtendService.getAllResourceExtends(serviceContext);
		if(JCollectionUtils.hasInCollect(resourceExtends)){
			for(ResourceExtend resourceExtend:resourceExtends){
				WebRequestURLCacheModelImpl cacheModel=new WebRequestURLCacheModelImpl();
				cacheModel.setUrl(resourceExtend.getUrl());
				cacheModel.setCached("Y".equals(resourceExtend.getCached()));
				cacheModels.add(cacheModel);
			}
		}
		return cacheModels;
	}

}
