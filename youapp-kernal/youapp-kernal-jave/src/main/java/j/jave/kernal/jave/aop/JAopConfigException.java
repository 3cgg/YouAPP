package j.jave.kernal.jave.aop;

public class JAopConfigException extends RuntimeException {


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
