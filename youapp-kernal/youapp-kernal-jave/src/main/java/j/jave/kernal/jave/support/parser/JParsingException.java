/**
 * 
 */
package j.jave.kernal.jave.support.parser;

/**
 * @author J
 */
public class JParsingException extends RuntimeException {

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
