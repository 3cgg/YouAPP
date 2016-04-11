/**
 * 
 */
package j.jave.kernal.jave.support.databind;

/**
 * @author J
 */
public class DataBindException extends RuntimeException {

	public DataBindException(String message){
		super(message);
	}
	
	public DataBindException(Exception e){
		super(e);
	}
	
	
}
