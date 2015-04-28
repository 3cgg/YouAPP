/**
 * 
 */
package j.jave.framework.utils;

/**
 * @author J
 */
public class UtilException extends RuntimeException {

	public UtilException() {
		super();
	}

	public UtilException(String message) {
		super(message);
	}

	public UtilException(Exception e) {
		super(e);
	}
	
}
