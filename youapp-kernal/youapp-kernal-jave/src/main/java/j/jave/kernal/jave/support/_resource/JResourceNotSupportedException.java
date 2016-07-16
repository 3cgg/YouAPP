package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

public class JResourceNotSupportedException extends JNestedRuntimeException {
	
	public JResourceNotSupportedException(String message){
		super(message);
	} 
	
	public JResourceNotSupportedException(Exception e){
		super(e);
	} 
	
}
