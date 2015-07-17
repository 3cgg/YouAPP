/**
 * 
 */
package j.jave.framework.commons.persist;

public class JPersistException extends RuntimeException {

	public JPersistException(String message){
		super(message);
	}
	
	public JPersistException(Exception e){
		super(e);
	}
	
	
}
