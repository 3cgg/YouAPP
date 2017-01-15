package me.bunny.kernel._c.transaction;


public class JUnexpectedRollbackException extends JTransactionException {

	/**
	 * Constructor for UnexpectedRollbackException.
	 * @param msg the detail message
	 */
	public JUnexpectedRollbackException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for UnexpectedRollbackException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	public JUnexpectedRollbackException(String msg, Throwable cause) {
		super(msg, cause);
	}

}