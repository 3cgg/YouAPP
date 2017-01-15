/**
 * 
 */
package me.bunny.kernel._c.reflect;

/**
 *
 * @author J
 */
public class JPropertyNotFoundException extends RuntimeException {

	public JPropertyNotFoundException(String message){
		super(message);
	}
	
	public JPropertyNotFoundException(Exception e){
		super(e);
	}
	
	
}
