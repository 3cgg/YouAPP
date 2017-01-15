package me.bunny.kernel._c.transaction;


public class JInvalidTimeoutException extends JTransactionUsageException {

	private int timeout;


	/**
	 * Constructor for InvalidTimeoutException.
	 * @param msg the detail message
	 * @param timeout the invalid timeout value
	 */
	public JInvalidTimeoutException(String msg, int timeout) {
		super(msg);
		this.timeout = timeout;
	}

	/**
	 * Return the invalid timeout value.
	 */
	public int getTimeout() {
		return timeout;
	}

}