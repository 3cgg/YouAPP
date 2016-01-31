/**
 * 
 */
package j.jave.kernal.jave.json.exception;

/**
 * any concurrent state can be notified by the exception. 
 * @author J
 */
public class JJSONException extends RuntimeException {
	
	private static final long serialVersionUID = 5276076781917641882L;

	public JJSONException(String message){
		super(message);
	}
	
	public JJSONException(Exception e){
		super(e);
	}
	
	
}
