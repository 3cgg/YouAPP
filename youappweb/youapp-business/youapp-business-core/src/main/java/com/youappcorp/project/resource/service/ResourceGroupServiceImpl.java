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
import com.youappcorp.project.resource.model.ResourceGroup;
import com.youappcorp.project.resource.repo.ResourceGroupRepo;

@Service(value="resourceGroupServiceImpl.transation.jpa")
public class ResourceGroupServiceImpl extends InternalServiceSupport<ResourceGroup> implements ResourceGroupService {

	@Autowired
	private ResourceGroupRepo<?> resourceGroupMapper;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	public JIPersist<?, ResourceGroup> getRepo() {
		return resourceGroupMapper;
	}
	
	@Override
	public List<ResourceGroup> getResourceGroupsByResourceId(
			ServiceContext serviceContext, String resourceId) {
		return resourceGroupMapper.getResourceGroupsByResourceId(resourceId);
	}

	@Override
	public int countOnResourceIdAndGroupId(ServiceContext serviceContext,
			String resourceId, String groupId) {
		return resourceGroupMapper.countOnResourceIdAndGroupId(resourceId, groupId);
	}

	@Override
	public ResourceGroup getResourceGroupOnResourceIdAndGroupId(
			ServiceContext serviceContext, String resourceId, String groupId) {
		return resourceGroupMapper.getResourceGroupOnResourceIdAndGroupId(resourceId, groupId);
	}
	
	
	@Override
	public ResourceGroup bingResourceGroup(ServiceContext serviceContext, String resourceId,
			String groupId) throws BusinessException {
		ResourceGroup resourceGroup=null;
		try{
			if(isBing(serviceContext, resourceId, groupId)){
				throw new BusinessException("the resource had already belong to the group.");
			}
			resourceGroup=new ResourceGroup();
			resourceGroup.setResourceId(resourceId);
			resourceGroup.setGroupId(groupId);
			resourceGroup.setId(JUniqueUtils.unique());
			saveOnly(serviceContext, resourceGroup);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceGroup;
	}
	
	@Override
	public ResourceGroup bingResourcePathGroup(ServiceContext serviceContext,
			String path, String groupId) throws BusinessException {
		ResourceGroup resourceGroup=null;
		try{
			Resource resource= resourceService.getResourceByURL(serviceContext, path);
			if(resource==null){
				resource=new Resource();
				resourceService.saveResource(serviceContext, resource);
			}
			resourceGroup=bingResourceGroup(serviceContext, resource.getId(), groupId);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceGroup;
		
	}
	
	@Override
	public void unbingResourceGroup(ServiceContext serviceContext, String resourceId,
			String groupId) throws BusinessException {
		try{
			ResourceGroup resourceGroup=getResourceGroupOnResourceIdAndGroupId(serviceContext, resourceId, groupId);
			if(resourceGroup==null){
				throw new BusinessException("the resource had not already belong to the group.");
			}
			delete(serviceContext, resourceGroup.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String resourceId,
			String groupId) {
		int count=resourceGroupMapper.countOnResourceIdAndGroupId(resourceId, groupId);
		return count>0;
	}
	
	
}
