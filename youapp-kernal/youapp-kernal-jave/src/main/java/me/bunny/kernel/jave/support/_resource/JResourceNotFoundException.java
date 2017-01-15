package me.bunny.kernel.jave.support._resource;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

public class JResourceNotFoundException extends JNestedRuntimeException {
	
	public JResourceNotFoundException(String message){
		super(message);
	} 
	
	public JResourceNotFoundException(Exception e){
		super(e);
	} 
	
}
