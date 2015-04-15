/**
 * 
 */
package j.jave.framework.servicehub;

/**
 * @author J
 */
public class JServiceException extends Exception {

	public JServiceException(String message){
		super(message);
	}
	
	public JServiceException(Exception e){
		super(e);
	}
}
