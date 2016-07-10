package j.jave.kernal.jave.transaction;


/**
 * Exception thrown when a transaction can't be created using an
 * underlying transaction API such as JTA.
 *
 * @author Rod Johnson
 * @since 17.03.2003
 */
@SuppressWarnings("serial")
public class JCannotCreateTransactionException extends JTransactionException {

	/**
	 * Constructor for CannotCreateTransactionException.
	 * @param msg the detail message
	 */
	public JCannotCreateTransactionException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for CannotCreateTransactionException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	public JCannotCreateTransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
