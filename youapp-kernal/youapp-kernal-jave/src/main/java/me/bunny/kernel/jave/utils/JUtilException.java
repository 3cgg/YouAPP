/**
 * 
 */
package me.bunny.kernel.jave.utils;

/**
 * @author J
 */
public class JUtilException extends RuntimeException {

	public JUtilException() {
		super();
	}

	public JUtilException(String message) {
		super(message);
	}

	public JUtilException(Exception e) {
		super(e);
	}
	
}
