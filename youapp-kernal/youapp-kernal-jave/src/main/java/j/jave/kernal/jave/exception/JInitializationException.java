/**
 * 
 */
package j.jave.kernal.jave.exception;

/**
 * any case of initializing can throw this exception to notify others.
 * @author J
 */
public class JInitializationException extends RuntimeException {
	
	private static final long serialVersionUID = 5276076781917641882L;

	public JInitializationException(String message){
		super(message);
	}
	
	public JInitializationException(Exception e){
		super(e);
	}
	
	
}
