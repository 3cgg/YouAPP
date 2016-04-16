package com.youappcorp.project.resource.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import com.youappcorp.project.resource.model.Resource;
import com.youappcorp.project.resource.model.ResourceRole;
import com.youappcorp.project.resource.repo.ResourceRoleRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceRoleServiceImpl.transation.jpa")
public class ResourceRoleServiceImpl extends ServiceSupport<ResourceRole> implements ResourceRoleService {

	@Autowired
	private ResourceRoleRepo<?> resourceRoleMapper;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	public JIPersist<?, ResourceRole> getRepo() {
		return resourceRoleMapper;
	}
	
	@Override
	public List<ResourceRole> getResourceRolesByResourceId(
			ServiceContext serviceContext, String resourceId) {
		return resourceRoleMapper.getResourceRolesByResourceId(resourceId);
	}

	@Override
	public int countOnResourceIdAndRoleId(ServiceContext serviceContext,
			String resourceId, String roleId) {
		return resourceRoleMapper.countOnResourceIdAndRoleId(resourceId, roleId);
	}

	@Override
	public ResourceRole getResourceRoleOnResourceIdAndRoleId(
			ServiceContext serviceContext, String resourceId, String roleId) {
		return resourceRoleMapper.getResourceRoleOnResourceIdAndRoleId(resourceId, roleId);
	}
	
	
	@Override
	public ResourceRole bingResourceRole(ServiceContext serviceContext, String resourceId,
			String roleId) throws JServiceException {
		if(isBing(serviceContext, resourceId, roleId)){
			throw new JServiceException("the resource had already belong to the role.");
		}
		
		ResourceRole resourceRole=new ResourceRole();
		resourceRole.setResourceId(resourceId);
		resourceRole.setRoleId(roleId);
		resourceRole.setId(JUniqueUtils.unique());
		saveOnly(serviceContext, resourceRole);
		return resourceRole;
	}
	
	@Override
	public ResourceRole bingResourcePathRole(ServiceContext serviceContext,
			String path, String roleId) throws JServiceException {
		Resource resource= resourceService.getResourceByURL(serviceContext, path);
		if(resource==null){
			resource=new Resource();
			resource.setUrl(path); 
			resourceService.saveResource(serviceContext, resource);
		}
		return bingResourceRole(serviceContext, resource.getId(), roleId);
	}
	
	@Override
	public void unbingResourceRole(ServiceContext serviceContext, String resourceId,
			String roleId) throws JServiceException {
		
		ResourceRole resourceRole=getResourceRoleOnResourceIdAndRoleId(serviceContext, resourceId, roleId);
		if(resourceRole==null){
			throw new JServiceException("the resource had not already belong to the role.");
		}
		delete(serviceContext, resourceRole.getId());
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String resourceId,
			String roleId) {
		int count=resourceRoleMapper.countOnResourceIdAndRoleId(resourceId, roleId);
		return count>0;
	}
	
	
	
}
