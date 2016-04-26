/**
 * 
 */
package j.jave.kernal.container;

/**
 * any method ( statement ) can throw this exception to notify the caller. 
 * @author J
 */
public class JURIExecuteException extends RuntimeException {
	
	private static final long serialVersionUID = 638712363288955742L;

	public JURIExecuteException(String message){
		super(message);
	}
	
	public JURIExecuteException(Exception e){
		super(e);
	}
	
	
}
