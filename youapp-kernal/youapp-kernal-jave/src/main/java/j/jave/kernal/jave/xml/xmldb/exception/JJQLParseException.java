/**
 * 
 */
package j.jave.kernal.jave.xml.xmldb.exception;

/**
 * any exception occurs while parsing jql. 
 * @author J
 */
public class JJQLParseException extends RuntimeException {

	public JJQLParseException(String message){
		super(message);
	}
	
	public JJQLParseException(Exception e){
		super(e);
	}
	
	public JJQLParseException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
