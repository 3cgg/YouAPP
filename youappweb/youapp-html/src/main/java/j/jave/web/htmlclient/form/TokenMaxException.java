/**
 * 
 */
package j.jave.web.htmlclient.form;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

public class TokenMaxException extends JNestedRuntimeException {

	public TokenMaxException(String message){
		super(message);
	}
	
	public TokenMaxException(Exception e){
		super(e);
	}
	
	
}
