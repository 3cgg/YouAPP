package j.jave.framework.components.resource.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.resource.model.Resource;

import java.util.List;

public interface ResourceService extends Service<Resource> {

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
	void saveResource(ServiceContext context,Resource resource) throws JServiceException;
	
	
}
