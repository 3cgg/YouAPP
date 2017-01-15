/**
 * 
 */
package me.bunny.kernel.eventdriven.exception;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

/**
 * any service provider can throw the exception to notify any exception occurs , 
 * that indicates the service is error.  
 * @author J
 */
public class JUncheckedServiceException extends JNestedRuntimeException {

	public JUncheckedServiceException(String message){
		super(message);
	}
	
	public JUncheckedServiceException(Exception e){
		super(e);
	}
	
}
