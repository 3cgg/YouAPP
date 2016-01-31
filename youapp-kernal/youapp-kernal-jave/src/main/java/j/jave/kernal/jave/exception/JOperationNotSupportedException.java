/**
 * 
 */
package j.jave.kernal.jave.exception;

/**
 * any method ( statement ) can throw this exception to notify the caller , until this version the operation can not be supported. 
 * @author J
 */
public class JOperationNotSupportedException extends RuntimeException {
	
	private static final long serialVersionUID = 638712363288955742L;

	public JOperationNotSupportedException(String message){
		super(message);
	}
	
	public JOperationNotSupportedException(Exception e){
		super(e);
	}
	
	
}
