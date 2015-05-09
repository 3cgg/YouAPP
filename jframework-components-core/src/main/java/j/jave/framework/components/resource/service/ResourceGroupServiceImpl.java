package j.jave.framework.components.resource.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.resource.mapper.ResourceGroupMapper;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.utils.JUniqueUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceGroupServiceImpl.transation")
public class ResourceGroupServiceImpl extends ServiceSupport<ResourceGroup> implements ResourceGroupService {

	@Autowired
	private ResourceGroupMapper resourceGroupMapper;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	protected JMapper<ResourceGroup> getMapper() {
		return this.resourceGroupMapper;
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
