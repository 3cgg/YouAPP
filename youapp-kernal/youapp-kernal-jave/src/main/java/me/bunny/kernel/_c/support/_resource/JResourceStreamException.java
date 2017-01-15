package me.bunny.kernel._c.support._resource;

import me.bunny.kernel._c.exception.JNestedRuntimeException;

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
