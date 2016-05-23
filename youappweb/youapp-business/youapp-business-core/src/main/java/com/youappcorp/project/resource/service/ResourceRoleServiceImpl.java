package com.youappcorp.project.resource.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.resource.model.Resource;
import com.youappcorp.project.resource.model.ResourceRole;
import com.youappcorp.project.resource.repo.ResourceRoleRepo;

@Service(value="resourceRoleServiceImpl.transation.jpa")
public class ResourceRoleServiceImpl extends InternalServiceSupport<ResourceRole> implements ResourceRoleService {

	@Autowired
	private ResourceRoleRepo<?> resourceRoleMapper;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	public JIPersist<?, ResourceRole, String> getRepo() {
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
			String roleId) throws BusinessException {
		ResourceRole resourceRole=null;
		try{

			if(isBing(serviceContext, resourceId, roleId)){
				throw new BusinessException("the resource had already belong to the role.");
			}
			
			resourceRole=new ResourceRole();
			resourceRole.setResourceId(resourceId);
			resourceRole.setRoleId(roleId);
			resourceRole.setId(JUniqueUtils.unique());
			saveOnly(serviceContext, resourceRole);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceRole;
	}
	
	@Override
	public ResourceRole bingResourcePathRole(ServiceContext serviceContext,
			String path, String roleId) throws BusinessException {
		ResourceRole resourceRole=null;
		try{
			Resource resource= resourceService.getResourceByURL(serviceContext, path);
			if(resource==null){
				resource=new Resource();
				resource.setUrl(path); 
				resourceService.saveResource(serviceContext, resource);
			}
			resourceRole= bingResourceRole(serviceContext, resource.getId(), roleId);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceRole;
	}
	
	@Override
	public void unbingResourceRole(ServiceContext serviceContext, String resourceId,
			String roleId) throws BusinessException {
		try{
			ResourceRole resourceRole=getResourceRoleOnResourceIdAndRoleId(serviceContext, resourceId, roleId);
			if(resourceRole==null){
				throw new BusinessException("the resource had not already belong to the role.");
			}
			delete(serviceContext, resourceRole.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String resourceId,
			String roleId) {
		int count=resourceRoleMapper.countOnResourceIdAndRoleId(resourceId, roleId);
		return count>0;
	}
	
	
	
}
