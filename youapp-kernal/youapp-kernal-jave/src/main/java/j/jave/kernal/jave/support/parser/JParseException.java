/**
 * 
 */
package j.jave.kernal.jave.support.parser;

/**
 * @author J
 */
public class JParseException extends RuntimeException {

	public JParseException(String message){
		super(message);
	}
	
	public JParseException(Exception e){
		super(e);
	}
	
	
}
