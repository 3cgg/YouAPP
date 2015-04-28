/**
 * 
 */
package j.jave.framework.components.core.exception;

/**
 * any service provider can throw the exception to notify any exception occurs , 
 * that indicates the service is error.  
 * @author J
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(Exception e){
		super(e);
	}
	
}
