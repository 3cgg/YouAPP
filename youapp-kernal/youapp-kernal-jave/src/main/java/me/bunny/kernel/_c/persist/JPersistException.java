/**
 * 
 */
package me.bunny.kernel._c.persist;

public class JPersistException extends RuntimeException {

	public JPersistException(String message){
		super(message);
	}
	
	public JPersistException(Exception e){
		super(e);
	}
	
	
}
