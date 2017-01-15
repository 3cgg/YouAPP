package me.bunny.kernel._c.support._resource;

import me.bunny.kernel._c.exception.JNestedRuntimeException;

public class JResourceNotSupportedException extends JNestedRuntimeException {
	
	public JResourceNotSupportedException(String message){
		super(message);
	} 
	
	public JResourceNotSupportedException(Exception e){
		super(e);
	} 
	
}
