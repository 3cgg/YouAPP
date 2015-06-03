/**
 * 
 */
package j.jave.framework.servicehub.exception;

/**
 * any service provider can throw the exception to notify any exception occurs , 
 * that indicates the service is error.  
 * @author J
 */
public class JUncheckedServiceException extends RuntimeException {

	public JUncheckedServiceException(String message){
		super(message);
	}
	
	public JUncheckedServiceException(Exception e){
		super(e);
	}
	
}
