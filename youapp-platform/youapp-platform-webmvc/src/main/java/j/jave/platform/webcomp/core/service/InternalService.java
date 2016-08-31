package j.jave.platform.webcomp.core.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.service.JService;

import java.io.Serializable;

/**
 * service template.
 * @author J
 *
 * @param <T>
 */
public interface InternalService<T extends JModel,ID extends Serializable> extends JService {

	/**
	 * insert record . not include other business. 
	 * @param object
	 * @throws JServiceException
	 */
	public void saveOnly( T object) throws JServiceException;
	
	/**
	 * update record . not include other business. 
	 * @param object
	 * @throws JServiceException
	 */
	public void updateOnly( T object) throws JServiceException;

	/**
	 * make the record not available
	 * @bill id
	 */
	public void delete( ID id);
	
	/**
	 * make the record not available
	 * @bill id
	 */
	public void delete( T id);
	
	/**
	 * get one by record identification. 
	 * @param id
	 * @return
	 */
	public T getById( ID id);
	
	/**
	 * get records for pagination.
	 * @param object class implements {@link JPageable}
	 * @return
	 */
	public JPage<T> getsByPage( JPageable pagination) ;
	
	
}
