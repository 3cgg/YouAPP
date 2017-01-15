/**
 * 
 */
package me.bunny.modular._p.mail.exception;

public class JMailException extends RuntimeException {

	public JMailException(String message){
		super(message);
	}
	
	public JMailException(Exception e){
		super(e);
	}
	
	
}
