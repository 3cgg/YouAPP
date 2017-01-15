package com.youappcorp.project.websupport.service;

import me.bunny.app._c._web.web.cache.resource.weburl.WebRequestURLCacheModel;
import me.bunny.app._c._web.web.cache.resource.weburl.WebRequestURLCacheModelService;
import me.bunny.kernel._c.utils.JCollectionUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.resourcemanager.model.ResourceRecord;
import com.youappcorp.project.resourcemanager.service.ResourceManagerService;
import com.youappcorp.project.websupport.model.WebRequestURLCacheModelImpl;

@Service
public class WebRequestURLCacheModelServiceImpl implements
		WebRequestURLCacheModelService {

	@Autowired
	private ResourceManagerService resourceManagerService;
	
	@Override
	public List<? extends WebRequestURLCacheModel> getResourceCacheModels() {
		List<WebRequestURLCacheModel> cacheModels=new ArrayList<WebRequestURLCacheModel>();
		List<ResourceRecord> resourceRecords= resourceManagerService.getResources();
		if(JCollectionUtils.hasInCollect(resourceRecords)){
			for(ResourceRecord resourceRecord:resourceRecords){
				WebRequestURLCacheModelImpl cacheModel=new WebRequestURLCacheModelImpl();
				cacheModel.setUrl(resourceRecord.getUrl());
				cacheModel.setCached("Y".equals(resourceRecord.getCached()));
				cacheModels.add(cacheModel);
			}
		}
		return cacheModels;
	}

}
