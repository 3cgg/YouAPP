/**
 * 
 */
package me.bunny.kernel._c.exception;

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
