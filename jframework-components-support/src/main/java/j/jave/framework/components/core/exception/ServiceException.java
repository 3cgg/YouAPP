/**
 * 
 */
package j.jave.framework.components.core.exception;

/**
 * @author Administrator
 *
 */
public class ServiceException extends Exception {

	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(Exception e){
		super(e);
	}
	
}
