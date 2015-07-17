/**
 * 
 */
package j.jave.framework.commons.exception;

public class JFormatException extends RuntimeException {

	public JFormatException(String message){
		super(message);
	}
	
	public JFormatException(Exception e){
		super(e);
	}
	
	
}
