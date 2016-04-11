/**
 * 
 */
package j.jave.kernal.jave.support.databind;

/**
 * @author J
 */
public class JDataBindException extends RuntimeException {

	public JDataBindException(String message){
		super(message);
	}
	
	public JDataBindException(Exception e){
		super(e);
	}
	
	
}
