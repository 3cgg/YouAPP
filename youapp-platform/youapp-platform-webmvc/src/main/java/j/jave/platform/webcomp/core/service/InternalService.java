package j.jave.platform.webcomp.core.service;

import java.io.Serializable;

import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JPageable;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.exception.JServiceException;

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
