package me.bunny.kernel._c.transaction;


public class JTransactionUsageException extends JTransactionException {

	/**
	 * Constructor for TransactionUsageException.
	 * @param msg the detail message
	 */
	public JTransactionUsageException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for TransactionUsageException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	public JTransactionUsageException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
