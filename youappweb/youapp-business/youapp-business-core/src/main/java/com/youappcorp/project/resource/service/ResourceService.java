package com.youappcorp.project.resource.service;

import j.jave.platform.webcomp.core.service.Service;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.resource.model.Resource;

public interface ResourceService extends Service<Resource, String> {

	/**
	 * get all resources from <code>RESOURCE</code> table. not any filter.
	 * @return
	 */
	List<Resource> getResources();
	
	/**
	 * get the resource according to the URL.
	 * @param context
	 * @param url
	 * @return
	 */
	Resource getResourceByURL(ServiceContext context,String url);
	
	/**
	 * save resource. 
	 * including additional validation process.
	 * @param context
	 * @param resource
	 */
	void saveResource(ServiceContext context,Resource resource) throws BusinessException;
	
	
}
