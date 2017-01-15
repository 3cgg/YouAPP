/**
 * 
 */
package me.bunny.kernel._c.support.parser;

import me.bunny.kernel._c.exception.JNestedRuntimeException;

/**
 * @author J
 */
public class JParsingException extends JNestedRuntimeException {

	public JParsingException(String message){
		super(message);
	}
	
	public JParsingException(Exception e){
		super(e);
	}
	
	public JParsingException(String message,Exception e){
		super(message,e);
	}
	
}
