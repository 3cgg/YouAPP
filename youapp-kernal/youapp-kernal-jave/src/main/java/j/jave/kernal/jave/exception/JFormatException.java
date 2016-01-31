/**
 * 
 */
package j.jave.kernal.jave.exception;

public class JFormatException extends RuntimeException {

	public JFormatException(String message){
		super(message);
	}
	
	public JFormatException(Exception e){
		super(e);
	}
	
	
}
