package j.jave.kernal.jave.transaction;


public class JIllegalTransactionStateException extends JTransactionUsageException {

	/**
	 * Constructor for IllegalTransactionStateException.
	 * @param msg the detail message
	 */
	public JIllegalTransactionStateException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for IllegalTransactionStateException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	public JIllegalTransactionStateException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
