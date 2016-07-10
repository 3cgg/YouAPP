package j.jave.kernal.jave.aop;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

public class JAopInvocationException extends JNestedRuntimeException{


	/**
	 * Constructor for AopInvocationException.
	 * @param msg the detail message
	 */
	public JAopInvocationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for AopInvocationException.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public JAopInvocationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
