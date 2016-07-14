package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

public class JResourceNotFoundException extends JNestedRuntimeException {
	
	public JResourceNotFoundException(String message){
		super(message);
	} 
	
	public JResourceNotFoundException(Exception e){
		super(e);
	} 
	
}
