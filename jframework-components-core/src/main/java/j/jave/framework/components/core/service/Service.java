package j.jave.framework.components.core.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.model.JPagination;

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
	 * @throws ServiceException
	 */
	public void saveOnly(ServiceContext context, T object) throws ServiceException;
	
	/**
	 * update record . not include other business. 
	 * @param context
	 * @param object
	 * @throws ServiceException
	 */
	public void updateOnly(ServiceContext context, T object) throws ServiceException;

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
