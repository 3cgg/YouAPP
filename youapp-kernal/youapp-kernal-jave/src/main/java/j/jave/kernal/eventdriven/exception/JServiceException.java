/**
 * 
 */
package j.jave.kernal.eventdriven.exception;

/**
 * any service provider can throw the exception to notify any exception occurs , 
 * that indicates the service is error.  
 * @author J
 */
public class JServiceException extends Exception {

	public JServiceException(String message){
		super(message);
	}
	
	public JServiceException(Exception e){
		super(e);
	}
	
}
