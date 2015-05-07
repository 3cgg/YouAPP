/**
 * 
 */
package j.jave.framework.servicehub;

/**
 * the exception thrown when event executed failure.
 * @author J
 */
public class JEventExecutionException extends RuntimeException {

	public JEventExecutionException(String message){
		super(message);
	}
	
	public JEventExecutionException(Throwable e){
		super(e);
	}
	
	
}
