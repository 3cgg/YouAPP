package me.bunny.kernel.jave.transaction;


/**
 * Exception thrown when attempting to work with a nested transaction
 * but nested transactions are not supported by the underlying backend.
 *
 * @author Juergen Hoeller
 * @since 1.1
 */
@SuppressWarnings("serial")
public class JNestedTransactionNotSupportedException extends JCannotCreateTransactionException {

	/**
	 * Constructor for NestedTransactionNotSupportedException.
	 * @param msg the detail message
	 */
	public JNestedTransactionNotSupportedException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for NestedTransactionNotSupportedException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	public JNestedTransactionNotSupportedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
