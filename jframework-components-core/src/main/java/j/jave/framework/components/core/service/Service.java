package j.jave.framework.components.core.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.model.JPagination;

import java.util.List;

/**
 * service template.
 * @author J
 *
 * @param <T>
 */
public interface Service<T> {

	/**
	 * insert record . not include other business. 
	 * @param context
	 * @param object
	 * @throws JServiceException
	 */
	public void saveOnly(ServiceContext context, T object) throws JServiceException;
	
	/**
	 * update record . not include other business. 
	 * @param context
	 * @param object
	 * @throws JServiceException
	 */
	public void updateOnly(ServiceContext context, T object) throws JServiceException;

	/**
	 * make the record not available
	 * @bill context
	 * @bill id
	 */
	public void delete(ServiceContext context, String id);
	
	
	/**
	 * get one by record identification. 
	 * @param context
	 * @param id
	 * @return
	 */
	public T getById(ServiceContext context, String id);
	
	/**
	 * get records for pagination.
	 * @param context
	 * @param object class implements {@link JPagination}
	 * @return
	 */
	public List<T> getsByPage(ServiceContext context, JPagination pagination) ;
	
	
}
