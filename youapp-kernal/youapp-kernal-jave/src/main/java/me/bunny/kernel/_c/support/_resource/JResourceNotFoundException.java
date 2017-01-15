package me.bunny.kernel._c.support._resource;

import me.bunny.kernel._c.exception.JNestedRuntimeException;

public class JResourceNotFoundException extends JNestedRuntimeException {
	
	public JResourceNotFoundException(String message){
		super(message);
	} 
	
	public JResourceNotFoundException(Exception e){
		super(e);
	} 
	
}
