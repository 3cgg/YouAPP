/**
 * 
 */
package j.jave.framework.commons.exception;

/**
 * any concurrent state can be notified by the exception. 
 * @author J
 */
public class JConcurrentException extends RuntimeException {

	public JConcurrentException(String message){
		super(message);
	}
	
	public JConcurrentException(Exception e){
		super(e);
	}
	
	
}
