/**
 * 
 */
package me.bunny.kernel.jave.support.parser;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

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
