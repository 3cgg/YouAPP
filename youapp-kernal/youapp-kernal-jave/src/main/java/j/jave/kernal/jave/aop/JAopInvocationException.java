package j.jave.kernal.jave.aop;

public class JAopInvocationException extends RuntimeException{


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


	/**
	 * Retrieve the innermost cause of this exception, if any.
	 * @return the innermost exception, or {@code null} if none
	 * @since 2.0
	 */
	public Throwable getRootCause() {
		Throwable rootCause = null;
		Throwable cause = getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}
	
}
