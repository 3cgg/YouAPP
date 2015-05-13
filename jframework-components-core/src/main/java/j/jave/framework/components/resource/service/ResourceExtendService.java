package j.jave.framework.components.resource.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.servicehub.exception.JServiceException;

public interface ResourceExtendService extends Service<ResourceExtend> {

	/**
	 * save resource extend. if the extend does not tie to any resource, we need save resource first.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 */
	void saveResourceExtend(ServiceContext context,ResourceExtend resourceExtend) throws JServiceException;
	
	
	/**
	 * enable resource cached.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 */
	void enableCache(ServiceContext context,ResourceExtend resourceExtend) throws JServiceException;
	
	/**
	 * disable resource cached.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 */
	void disableCache(ServiceContext context,ResourceExtend resourceExtend) throws JServiceException;
	
	/**
	 * enhance the resource, collection all additional functions, such as 
	 * <p> if resource url cache supported.
	 * @param context
	 * @param resourceExtend
	 * @throws JServiceException
	 * @return ResourceExtend , the latest record. 
	 */
	ResourceExtend enhanceResource(ServiceContext context,ResourceExtend resourceExtend) throws JServiceException;
	
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
