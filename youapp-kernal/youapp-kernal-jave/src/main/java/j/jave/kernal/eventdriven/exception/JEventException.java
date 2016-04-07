/**
 * 
 */
package j.jave.kernal.eventdriven.exception;

/**
 * the exception thrown when event executed failure.
 * @author J
 */
@SuppressWarnings("serial")
public class JEventException extends RuntimeException {
	
	public JEventException(String message){
		super(message);
	}
	
	public JEventException(Throwable e){
		super(e);
	}
	
	
}
