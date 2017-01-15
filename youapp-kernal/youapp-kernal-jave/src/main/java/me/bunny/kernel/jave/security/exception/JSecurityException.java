/**
 * 
 */
package me.bunny.kernel.jave.security.exception;

/**
 * any concurrent state can be notified by the exception. 
 * @author J
 */
public class JSecurityException extends RuntimeException {
	
	private static final long serialVersionUID = 5276076781917641882L;

	public JSecurityException(String message){
		super(message);
	}
	
	public JSecurityException(Exception e){
		super(e);
	}
	
	
}
