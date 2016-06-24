package com.youappcorp.project.resource.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.webcomp.core.service.InternalService;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.resource.model.ResourceExtend;

public interface ResourceExtendService extends InternalService<ResourceExtend, String> {

	/**
	 * save resource extend. if the extend does not tie to any resource, we need save resource first.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 */
	void saveResourceExtend(ServiceContext context,ResourceExtend resourceExtend) throws BusinessException;
	
	
	/**
	 * enable resource cached.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 */
	void enableCache(ServiceContext context,ResourceExtend resourceExtend) throws BusinessException;
	
	/**
	 * disable resource cached.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 */
	void disableCache(ServiceContext context,ResourceExtend resourceExtend) throws BusinessException;
	
	/**
	 * enhance the resource, collection all additional functions, such as 
	 * <p> if resource url cache supported.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 * @return ResourceExtend , the latest record. 
	 */
	ResourceExtend enhanceResource(ServiceContext context,ResourceExtend resourceExtend) throws BusinessException;
	
	/**
	 * get resource extension according to the resource id. 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	ResourceExtend getResourceExtendOnResourceId(ServiceContext context,String resourceId);
	
	/**
	 * get all active resource extends , i.e.  DELETE='N' 
	 * Note the resource related to is also active.
	 * @param context
	 * @return
	 */
	List<ResourceExtend> getAllResourceExtends(ServiceContext context);
	
}
