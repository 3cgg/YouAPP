package com.youappcorp.project.resource.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import com.youappcorp.project.resource.model.Resource;
import com.youappcorp.project.resource.model.ResourceGroup;
import com.youappcorp.project.resource.repo.ResourceGroupRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceGroupServiceImpl.transation")
public class ResourceGroupServiceImpl extends ServiceSupport<ResourceGroup> implements ResourceGroupService {

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
			String groupId) throws JServiceException {
		if(isBing(serviceContext, resourceId, groupId)){
			throw new JServiceException("the resource had already belong to the group.");
		}
		
		ResourceGroup resourceGroup=new ResourceGroup();
		resourceGroup.setResourceId(resourceId);
		resourceGroup.setGroupId(groupId);
		resourceGroup.setId(JUniqueUtils.unique());
		saveOnly(serviceContext, resourceGroup);
		return resourceGroup;
	}
	
	@Override
	public ResourceGroup bingResourcePathGroup(ServiceContext serviceContext,
			String path, String groupId) throws JServiceException {
		Resource resource= resourceService.getResourceByURL(serviceContext, path);
		if(resource==null){
			resource=new Resource();
			resourceService.saveResource(serviceContext, resource);
		}
		return bingResourceGroup(serviceContext, resource.getId(), groupId);
	}
	
	@Override
	public void unbingResourceGroup(ServiceContext serviceContext, String resourceId,
			String groupId) throws JServiceException {
		
		ResourceGroup resourceGroup=getResourceGroupOnResourceIdAndGroupId(serviceContext, resourceId, groupId);
		if(resourceGroup==null){
			throw new JServiceException("the resource had not already belong to the group.");
		}
		delete(serviceContext, resourceGroup.getId());
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String resourceId,
			String groupId) {
		int count=resourceGroupMapper.countOnResourceIdAndGroupId(resourceId, groupId);
		return count>0;
	}
	
	
}
