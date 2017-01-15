package me.bunny.kernel.jave.aop;

import me.bunny.kernel.jave.exception.JNestedRuntimeException;

public class JAopConfigException extends JNestedRuntimeException {


	/**
	 * Constructor for AopConfigException.
	 * @param msg the detail message
	 */
	public JAopConfigException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for AopConfigException.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public JAopConfigException(String msg, Throwable cause) {
		super(msg, cause);
	}


}
