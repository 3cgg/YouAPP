/**
 * 
 */
package j.jave.kernal.jave.support;

import j.jave.kernal.jave.service.JService;



/**
 * scan anything from anywhere.
 * @author J
 *
 */
public interface JScanner<T> extends JService{

	public T scan() ;
	
}
