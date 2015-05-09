package j.jave.framework.components.resource.service;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.servicehub.exception.JServiceException;

import java.util.List;

public interface ResourceGroupService extends Service<ResourceGroup> {


	
	/**
	 * get all resource groups according to resource id.
	 * @param serviceContext
	 * @param resourceId
	 * @return
	 */
	List<ResourceGroup> getResourceGroupsByResourceId(ServiceContext serviceContext,String resourceId);
	
	/**
	 * count of resource id and group id.
	 * @param serviceContext
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	int countOnResourceIdAndGroupId(ServiceContext serviceContext,String resourceId,String groupId);
	
	/**
	 * get resource group according to resource id and group id.
	 * @param serviceContext
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	ResourceGroup getResourceGroupOnResourceIdAndGroupId(ServiceContext serviceContext,String resourceId,String groupId);
	
	
	/**
	 * grant the resource to a group. 
	 * @param serviceContext
	 * @param resourceId
	 * @param groupId
	 * @return 
	 */
	ResourceGroup bingResourceGroup(ServiceContext serviceContext,String resourceId,String groupId) throws JServiceException;
	
	/**
	 * grant the resource to a group.  if the path does not exist, create new resource.
	 * @param serviceContext
	 * @param path the URL path, like /resource.resourceaction/bingResourceRole
	 * @param groupId
	 * @return 
	 */
	ResourceGroup bingResourcePathGroup(ServiceContext serviceContext,String path,String groupId) throws JServiceException;
	
	
	/**
	 * remove group on the resource. 
	 * @param serviceContext
	 * @param resourceId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingResourceGroup(ServiceContext serviceContext,String resourceId,String groupId) throws JServiceException;
	
	/**
	 * check if the resource had already belong  the group. 
	 * @param serviceContext
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	boolean isBing(ServiceContext serviceContext,String resourceId,String groupId);
	
	

}
