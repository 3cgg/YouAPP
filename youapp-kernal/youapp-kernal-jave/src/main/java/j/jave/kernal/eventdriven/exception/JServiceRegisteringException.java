/**
 * 
 */
package j.jave.kernal.eventdriven.exception;

/**
 * that indicates the service cannot be registered, as that already exits.
 * @author J
 */
public class JServiceRegisteringException extends RuntimeException {

	public JServiceRegisteringException(String message){
		super(message);
	}
	
	public JServiceRegisteringException(Exception e){
		super(e);
	}
	
}
