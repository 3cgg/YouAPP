/**
 * 
 */
package me.bunny.kernel.eventdriven.exception;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

/**
 * that indicates the service cannot be registered, as that already exits.
 * @author J
 */
public class JServiceRegisteringException extends JNestedRuntimeException {

	public JServiceRegisteringException(String message){
		super(message);
	}
	
	public JServiceRegisteringException(Exception e){
		super(e);
	}
	
}
