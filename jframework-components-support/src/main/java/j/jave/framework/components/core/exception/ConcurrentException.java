/**
 * 
 */
package j.jave.framework.components.core.exception;

/**
 * @author Administrator
 *
 */
public class ConcurrentException extends RuntimeException {
	
	public ConcurrentException(String message){
		super(message);
	}
	
	public ConcurrentException(Exception e){
		super(e);
	}
	
	
}
