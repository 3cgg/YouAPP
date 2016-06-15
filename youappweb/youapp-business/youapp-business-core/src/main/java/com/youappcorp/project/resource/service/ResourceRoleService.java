package com.youappcorp.project.resource.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.webcomp.core.service.Service;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.resource.model.ResourceRole;

public interface ResourceRoleService extends Service<ResourceRole, String> {

	
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
	int countOnResourceIdAndRoleId(ServiceContext serviceContext,String resourceId,String roleId);
	
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
	ResourceRole bingResourcePathRole(ServiceContext serviceContext,String path,String roleId) throws BusinessException;
	
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
	boolean isBing(ServiceContext serviceContext,String resourceId,String roleId);
	
	
}
