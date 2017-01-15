/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;

/**
 * the exception thrown when event executed failure.
 * @author J
 */
@SuppressWarnings("serial")
public class JEventExecutionException extends RuntimeException {

	/**
	 * the event is still not executed completely.
	 */
	public static final String EVENT_NOT_COMPLETE="EX0001";
	
	/**
	 * special error code. 
	 */
	private String code;
	
	public JEventExecutionException(String message){
		super(message);
	}
	
	
	
	public JEventExecutionException(String code,String message){
		super(message);
		this.code=code;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public JEventExecutionException(Throwable e){
		super(e);
	}
	
	
}
