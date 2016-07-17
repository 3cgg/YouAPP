/**
 * 
 */
package j.jave.kernal.jave.support.parser;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

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
