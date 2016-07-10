package j.jave.kernal.jave.transaction;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

public class JTransactionException extends JNestedRuntimeException {

	/**
	 * Constructor for TransactionException.
	 * @param msg the detail message
	 */
	public JTransactionException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for TransactionException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	public JTransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
