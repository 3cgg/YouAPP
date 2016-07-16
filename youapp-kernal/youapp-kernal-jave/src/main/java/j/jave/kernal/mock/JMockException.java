package j.jave.kernal.mock;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

public class JMockException extends JNestedRuntimeException {
	
	public JMockException(String message){
		super(message);
	} 
	
	public JMockException(Exception e){
		super(e);
	} 
	
}
