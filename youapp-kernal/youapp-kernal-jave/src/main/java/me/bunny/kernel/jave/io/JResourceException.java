package me.bunny.kernel.jave.io;

public class JResourceException extends RuntimeException {
	
	private final JResource resource;
	
	public JResourceException(JResource resource,String message,Exception e){
		super(message);
		this.resource=resource;
	} 
	
	public JResourceException(JResource resource,Exception e){
		super(e);
		this.resource=resource;
	} 
	
	public JResource getResource() {
		return resource;
	}
	
}
