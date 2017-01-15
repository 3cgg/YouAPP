package me.bunny.kernel.jave.support._resource;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

public class JResourceNotSupportedException extends JNestedRuntimeException {
	
	public JResourceNotSupportedException(String message){
		super(message);
	} 
	
	public JResourceNotSupportedException(Exception e){
		super(e);
	} 
	
}
