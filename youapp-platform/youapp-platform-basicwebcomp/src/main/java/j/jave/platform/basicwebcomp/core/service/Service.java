package j.jave.platform.basicwebcomp.core.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.service.JService;

/**
 * service template.
 * @author J
 *
 * @param <T>
 */
public interface Service<T extends JModel> extends JService {

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
	 * @param object class implements {@link JPageable}
	 * @return
	 */
	public JPage<T> getsByPage(ServiceContext context, JPageable pagination) ;
	
	
}
