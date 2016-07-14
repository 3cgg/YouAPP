/**
 * 
 */
package j.jave.kernal.eventdriven.exception;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

/**
 * the exception thrown when event executed failure.
 * @author J
 */
@SuppressWarnings("serial")
public class JEventException extends JNestedRuntimeException {
	
	public JEventException(String message){
		super(message);
	}
	
	public JEventException(Throwable e){
		super(e);
	}
	
	
}
