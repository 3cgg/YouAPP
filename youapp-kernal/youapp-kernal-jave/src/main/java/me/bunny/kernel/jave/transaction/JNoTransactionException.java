package me.bunny.kernel.jave.transaction;


public class JNoTransactionException extends JTransactionUsageException {

	/**
	 * Constructor for NoTransactionException.
	 * @param msg the detail message
	 */
	public JNoTransactionException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for NoTransactionException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	public JNoTransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}