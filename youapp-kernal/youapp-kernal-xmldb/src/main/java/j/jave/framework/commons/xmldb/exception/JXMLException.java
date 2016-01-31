/**
 * 
 */
package j.jave.framework.commons.xmldb.exception;

/**
 * any exception occurs while processing xml 
 * @author J
 */
public class JXMLException extends RuntimeException {

	public JXMLException(String message){
		super(message);
	}
	
	public JXMLException(Exception e){
		super(e);
	}
	
	
}
