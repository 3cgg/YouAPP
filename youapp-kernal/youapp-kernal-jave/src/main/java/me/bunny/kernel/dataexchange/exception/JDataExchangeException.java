/**
 * 
 */
package me.bunny.kernel.dataexchange.exception;

/**
 * @author J
 */
public class JDataExchangeException extends RuntimeException {

	public JDataExchangeException(String message){
		super(message);
	}
	
	public JDataExchangeException(Exception e){
		super(e);
	}
	
	
}
