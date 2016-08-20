package com.youappcorp.project.resourcemanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.resourcemanager.model.Resource;
import com.youappcorp.project.resourcemanager.model.ResourceGroup;
import com.youappcorp.project.resourcemanager.model.ResourceRecord;
import com.youappcorp.project.resourcemanager.model.ResourceRole;
import com.youappcorp.project.resourcemanager.vo.ResourceSearchCriteria;

public interface ResourceManagerService{

	/**
	 * @return
	 */
	List<ResourceRecord> getResources(ServiceContext serviceContext);
	
	List<ResourceRecord> getResources(ServiceContext serviceContext,ResourceSearchCriteria resourceSearchCriteria);
	
	JPage<ResourceRecord> getResourcesByPage(ServiceContext serviceContext,ResourceSearchCriteria resourceSearchCriteria,JSimplePageable simplePageable);
	
	/**
	 * get the resource according to the URL.
	 * @param serviceContext
	 * @param url
	 * @return
	 */
	ResourceRecord getResourceByURL(ServiceContext serviceContext,String url);
	
	/**
	 * get the resource according to the primary id.
	 * @param serviceContext
	 * @param id
	 * @return
	 */
	ResourceRecord getResourceById(ServiceContext serviceContext,String id);
	
	/**
	 * save resource. 
	 * including additional validation process.
	 * @param serviceContext
	 * @param resource
	 */
	void saveResource(ServiceContext serviceContext,Resource resource) throws BusinessException;
	
	/**
	 * enable resource cached.
	 * @param serviceContext
	 * @param resourceId
	 * @throws BusinessException
	 */
	void enableCache(ServiceContext serviceContext,String resourceId) throws BusinessException;
	
	/**
	 * disable resource cached.
	 * @param serviceContext
	 * @param resourceId
	 * @throws BusinessException
	 */
	void disableCache(ServiceContext serviceContext,String resourceId) throws BusinessException;
	
	
	/**
	 * get all resource roles according to resource id.
	 * @param serviceContext
	 * @param resourceId
	 * @return
	 */
	List<ResourceRole> getResourceRolesByResourceId(ServiceContext serviceContext,String resourceId);
	
	/**
	 * count of resource id and role id.
	 * @param serviceContext
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	long countOnResourceIdAndRoleId(ServiceContext serviceContext,String resourceId,String roleId);
	
	/**
	 * get resource role according to resource id and role id.
	 * @param serviceContext
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	ResourceRole getResourceRoleOnResourceIdAndRoleId(ServiceContext serviceContext,String resourceId,String roleId);
	
	/**
	 * grant the resource to a role. 
	 * @param serviceContext
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	ResourceRole bingResourceRole(ServiceContext serviceContext,String resourceId,String roleId) throws BusinessException;
	
	/**
	 * grant the resource to a role.  if the path does not exist, create new resource.
	 * @param serviceContext
	 * @param path the URL path, like /resource.resourceaction/bingResourceRole
	 * @param roleId
	 * @return
	 */
	ResourceRole bingResourceRoleByUrl(ServiceContext serviceContext,String path,String roleId) throws BusinessException;
	
	/**
	 * remove role on the resource. 
	 * @param serviceContext
	 * @param resourceId
	 * @param roleId
	 * @throws JServiceException
	 */
	void unbingResourceRole(ServiceContext serviceContext,String resourceId,String roleId) throws BusinessException;
	
	/**
	 * check if the resource had already belong  the role. 
	 * @param serviceContext
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	boolean isBingResourceRole(ServiceContext serviceContext,String resourceId,String roleId);
	
	
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
	long countOnResourceIdAndGroupId(ServiceContext serviceContext,String resourceId,String groupId);
	
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
	ResourceGroup bingResourceGroup(ServiceContext serviceContext,String resourceId,String groupId) throws BusinessException;
	
	/**
	 * grant the resource to a group.  if the path does not exist, create new resource.
	 * @param serviceContext
	 * @param path the URL path, like /resource.resourceaction/bingResourceRole
	 * @param groupId
	 * @return 
	 */
	ResourceGroup bingResourceGroupByUrl(ServiceContext serviceContext,String url,String groupId) throws BusinessException;
	
	
	/**
	 * remove group on the resource. 
	 * @param serviceContext
	 * @param resourceId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingResourceGroup(ServiceContext serviceContext,String resourceId,String groupId) throws BusinessException;
	
	/**
	 * check if the resource had already belong  the group. 
	 * @param serviceContext
	 * @param resourceId
	 * @param groupId
	 * @return
	 */
	boolean isBingResourceGroup(ServiceContext serviceContext,String resourceId,String groupId);
	
	boolean existsResourceByUrl(ServiceContext serviceContext,String url);
	
}
