package com.youappcorp.project.resourcemanager.service;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.resourcemanager.model.Resource;
import com.youappcorp.project.resourcemanager.model.ResourceGroup;
import com.youappcorp.project.resourcemanager.model.ResourceRecord;
import com.youappcorp.project.resourcemanager.model.ResourceRole;
import com.youappcorp.project.resourcemanager.vo.ResourceSearchCriteria;

import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel.eventdriven.exception.JServiceException;

public interface ResourceManagerService{

	/**
	 * @return
	 */
	List<ResourceRecord> getResources();
	
	List<ResourceRecord> getResources(ResourceSearchCriteria resourceSearchCriteria);
	
	JPage<ResourceRecord> getResourcesByPage(ResourceSearchCriteria resourceSearchCriteria,JSimplePageable simplePageable);
	
	/**
	 * get the resource according to the URL.
	 * 
	 * @param url
	 * @return
	 */
	ResourceRecord getResourceByURL(String url);
	
	/**
	 * get the resource according to the primary id.
	 * 
	 * @param id
	 * @return
	 */
	ResourceRecord getResourceById(String id);
	
	/**
	 * save resource. 
	 * including additional validation process.
	 * 
	 * @param resource
	 */
	void saveResource(Resource resource) throws BusinessException;
	
	/**
	 * enable resource cached.
	 * 
	 * @param resourceId
	 * @throws BusinessException
	 */
	void enableCache(String resourceId) throws BusinessException;
	
	/**
	 * disable resource cached.
	 * 
	 * @param resourceId
	 * @throws BusinessException
	 */
	void disableCache(String resourceId) throws BusinessException;
	
	
	/**
	 * get all resource roles according to resource id.
	 * 
	 * @param resourceId
	 * @return
	 */
	List<ResourceRole> getResourceRolesByResourceId(String resourceId);
	
	/**
	 * count of resource id and role id.
	 * 
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	long countOnResourceIdAndRoleId(String resourceId,String roleId);
	
	/**
	 * get resource role according to resource id and role id.
	 * 
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	ResourceRole getResourceRoleOnResourceIdAndRoleId(String resourceId,String roleId);
	
	/**
	 * grant the resource to a role. 
	 * 
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	ResourceRole bingResourceRole(String resourceId,String roleId) throws BusinessException;
	
	/**
	 * grant the resource to a role.  if the path does not exist, create new resource.
	 * 
	 * @param path the URL path, like /resource.resourceaction/bingResourceRole
	 * @param roleId
	 * @return
	 */
	ResourceRole bingResourceRoleByUrl(String path,String roleId) throws BusinessException;
	
	/**
	 * remove role on the resource. 
	 * 
	 * @param resourceId
	 * @param roleId
	 * @throws JServiceException
	 */
	void unbingResourceRole(String resourceId,String roleId) throws BusinessException;
	
	/**
	 * check if the resource had already belong  the role. 
	 * 
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	boolean isBingResourceRole(String resourceId,String roleId);
	
	
	/**
	 * get all resource groups according to resource id.
	 * 
	 * @param resourceId
	 * @return
	 */
	List<ResourceGroup> getResourceGroupsByResourceId(String resourceId);
	
	/**
	 * count of resource id and group id.
	 * 
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	long countOnResourceIdAndGroupId(String resourceId,String groupId);
	
	/**
	 * get resource group according to resource id and group id.
	 * 
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	ResourceGroup getResourceGroupOnResourceIdAndGroupId(String resourceId,String groupId);
	
	
	/**
	 * grant the resource to a group. 
	 * 
	 * @param resourceId
	 * @param groupId
	 * @return 
	 */
	ResourceGroup bingResourceGroup(String resourceId,String groupId) throws BusinessException;
	
	/**
	 * grant the resource to a group.  if the path does not exist, create new resource.
	 * 
	 * @param path the URL path, like /resource.resourceaction/bingResourceRole
	 * @param groupId
	 * @return 
	 */
	ResourceGroup bingResourceGroupByUrl(String url,String groupId) throws BusinessException;
	
	
	/**
	 * remove group on the resource. 
	 * 
	 * @param resourceId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingResourceGroup(String resourceId,String groupId) throws BusinessException;
	
	/**
	 * check if the resource had already belong  the group. 
	 * 
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	boolean isBingResourceGroup(String resourceId,String groupId);
	
	boolean existsResourceByUrl(String url);
	
}
