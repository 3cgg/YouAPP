package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

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
