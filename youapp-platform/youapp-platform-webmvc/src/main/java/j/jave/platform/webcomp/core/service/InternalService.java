package j.jave.platform.webcomp.core.service;

import java.io.Serializable;

import me.bunny.kernel.eventdriven.exception.JServiceException;
import me.bunny.kernel.jave.model.JModel;
import me.bunny.kernel.jave.model.JPage;
import me.bunny.kernel.jave.model.JPageable;
import me.bunny.kernel.jave.service.JService;

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
