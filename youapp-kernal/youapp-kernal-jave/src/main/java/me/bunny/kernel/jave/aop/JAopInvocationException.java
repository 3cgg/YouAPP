package me.bunny.kernel.jave.aop;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

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
