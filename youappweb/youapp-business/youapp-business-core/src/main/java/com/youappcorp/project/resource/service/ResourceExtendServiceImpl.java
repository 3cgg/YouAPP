package com.youappcorp.project.resource.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.resource.model.Resource;
import com.youappcorp.project.resource.model.ResourceExtend;
import com.youappcorp.project.resource.repo.ResourceExtendRepo;

@Service(value="resourceExtendServiceImpl.transation.jpa")
public class ResourceExtendServiceImpl extends InternalServiceSupport<ResourceExtend> implements ResourceExtendService {

	@Autowired
	private ResourceExtendRepo<?> resourceExtendMapper;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	public JIPersist<?, ResourceExtend, String> getRepo() {
		return resourceExtendMapper;
	}
	
	@Override
	public void saveResourceExtend(ServiceContext context,
			ResourceExtend resourceExtend) throws BusinessException {
		try{
			// store resource first. 
			if(JStringUtils.isNullOrEmpty(resourceExtend.getResourceId())){
				Resource resource=resourceExtend.getResource();
				resourceService.saveResource(context, resource);
				resourceExtend.setResourceId(resource.getId());
			}
			
			if(JStringUtils.isNullOrEmpty(resourceExtend.getCached())){
				resourceExtend.setCached("N");
			}
			
			saveOnly(context, resourceExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void enableCache(ServiceContext context,
			ResourceExtend resourceExtend) throws BusinessException {
		try{
			// create resource extend
			if(JStringUtils.isNullOrEmpty(resourceExtend.getId())){
				saveResourceExtend(context, resourceExtend);
			}
			
			if("N".equals(resourceExtend.getCached())||JStringUtils.isNullOrEmpty(resourceExtend.getCached())){
				resourceExtendMapper.updateCached(resourceExtend.getId(), "Y");
			}
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void disableCache(ServiceContext context,
			ResourceExtend resourceExtend) throws BusinessException {
		try{
			// create resource extend
			if(JStringUtils.isNullOrEmpty(resourceExtend.getId())){
				saveResourceExtend(context, resourceExtend);
			}
			
			if("Y".equals(resourceExtend.getCached())||JStringUtils.isNullOrEmpty(resourceExtend.getCached())){
				resourceExtendMapper.updateCached(resourceExtend.getId(), "N");
			}
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public ResourceExtend enhanceResource(ServiceContext context,
			ResourceExtend resourceExtend) throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(resourceExtend.getResourceId())){
				Resource resource=resourceService.getResourceByURL(context, resourceExtend.getUrl());
				// if create new resource
				if(resource==null){
					resource=new Resource();
					resource.setUrl(resourceExtend.getUrl());
					resourceService.saveResource(context, resource);
					resourceExtend.setResourceId(resource.getId());
				}
				else{
					resourceExtend.setResourceId(resource.getId());
				}
			}
			
			//if create new resource extend
			if(JStringUtils.isNullOrEmpty(resourceExtend.getId())){
				
				ResourceExtend dbResourceExtend=resourceExtendMapper.getResourceExtendOnResourceId(resourceExtend.getResourceId());
				// create new resource extend , set all default value.
				if(dbResourceExtend==null){
					saveResourceExtend(context, resourceExtend);
					return resourceExtend;
				}
				else{
					resourceExtend.setId(dbResourceExtend.getId());
				}
			}
			
			// update different states. 
			
			if(JStringUtils.isNotNullOrEmpty(resourceExtend.getCached())){
				resourceExtendMapper.updateCached(resourceExtend.getId(), resourceExtend.getCached());
			}
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceExtend;
	}
	
	
	@Override
	public ResourceExtend getResourceExtendOnResourceId(ServiceContext context,
			String resourceId) {
		return resourceExtendMapper.getResourceExtendOnResourceId(resourceId);
	}

	@Override
	public List<ResourceExtend> getAllResourceExtends(ServiceContext context) {
		return resourceExtendMapper.getAllResourceExtends();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
