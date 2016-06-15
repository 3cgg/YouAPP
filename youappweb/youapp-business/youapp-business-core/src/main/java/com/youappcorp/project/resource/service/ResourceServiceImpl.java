package com.youappcorp.project.resource.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.resource.model.Resource;
import com.youappcorp.project.resource.repo.ResourceRepo;

@Service(value="resourceServiceImpl.transation.jpa")
public class ResourceServiceImpl extends InternalServiceSupport<Resource> implements ResourceService {

	@Autowired
	private ResourceRepo<?> resourceMapper;
	
	@Override
	public JIPersist<?, Resource, String> getRepo() {
		return this.resourceMapper;
	}
	
	@Override
	public List<Resource> getResources() {
		return resourceMapper.getResources();
	}
	
	@Override
	public Resource getResourceByURL(ServiceContext context, String url) {
		return resourceMapper.getResourceByURL(url);
	}
	
	@Override
	public void saveResource(ServiceContext context, Resource resource) throws BusinessException{
		try{
			Resource dbResource=getResourceByURL(context, resource.getUrl());
			if(dbResource!=null){
				throw new BusinessException("duplicate url : "+resource.getUrl());
			}
			saveOnly(context, resource);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	
}
