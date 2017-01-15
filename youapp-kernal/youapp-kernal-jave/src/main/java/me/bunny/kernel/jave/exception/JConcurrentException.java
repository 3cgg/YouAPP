/**
 * 
 */
package me.bunny.kernel.jave.exception;

/**
 * any concurrent state can be notified by the exception. 
 * @author J
 */
public class JConcurrentException extends JNestedRuntimeException {

	public JConcurrentException(String message){
		super(message);
	}
	
	public JConcurrentException(Exception e){
		super(e);
	}
	
	
}
