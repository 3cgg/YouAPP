package me.bunny.kernel.jave.support._resource;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

public class JResourceStreamException extends JNestedRuntimeException {
	
	public JResourceStreamException(String message){
		super(message);
	} 
	
	public JResourceStreamException(String message,Exception e){
		super(message,e);
	} 
	
	public JResourceStreamException(Exception e){
		super(e);
	} 
	
}
