/**
 * 
 */
package j.jave.kernal.jave.exception;

public class JFormatException extends JNestedRuntimeException {

	public JFormatException(String message){
		super(message);
	}
	
	public JFormatException(Exception e){
		super(e);
	}
	
	
}
