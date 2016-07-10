package j.jave.kernal.jave.transaction;

import java.sql.SQLException;
import java.sql.Savepoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenient base class for JDBC-aware transaction objects.
 * Can contain a {@link ConnectionHolder}, and implements the
 * {@link org.springframework.transaction.SavepointManager}
 * interface based on that ConnectionHolder.
 *
 * <p>Allows for programmatic management of JDBC 3.0
 * {@link java.sql.Savepoint Savepoints}. Spring's
 * {@link org.springframework.transaction.support.DefaultTransactionStatus}
 * will automatically delegate to this, as it autodetects transaction
 * objects that implement the SavepointManager interface.
 *
 * @author Juergen Hoeller
 * @since 1.1
 */
public abstract class JJdbcTransactionObjectSupport implements JSavepointManager, JSmartTransactionObject {

	private static final Log logger = LogFactory.getLog(JJdbcTransactionObjectSupport.class);


	private JConnectionHolder connectionHolder;

	private Integer previousIsolationLevel;

	private boolean savepointAllowed = false;


	public void setConnectionHolder(JConnectionHolder connectionHolder) {
		this.connectionHolder = connectionHolder;
	}

	public JConnectionHolder getConnectionHolder() {
		return this.connectionHolder;
	}

	public boolean hasConnectionHolder() {
		return (this.connectionHolder != null);
	}

	public void setPreviousIsolationLevel(Integer previousIsolationLevel) {
		this.previousIsolationLevel = previousIsolationLevel;
	}

	public Integer getPreviousIsolationLevel() {
		return this.previousIsolationLevel;
	}

	public void setSavepointAllowed(boolean savepointAllowed) {
		this.savepointAllowed = savepointAllowed;
	}

	public boolean isSavepointAllowed() {
		return this.savepointAllowed;
	}

	@Override
	public void flush() {
		// no-op
	}


	//---------------------------------------------------------------------
	// Implementation of SavepointManager
	//---------------------------------------------------------------------

	/**
	 * This implementation creates a JDBC 3.0 Savepoint and returns it.
	 * @see java.sql.Connection#setSavepoint
	 */
	@Override
	public Object createSavepoint() throws JTransactionException {
		JConnectionHolder conHolder = getConnectionHolderForSavepoint();
		try {
			if (!conHolder.supportsSavepoints()) {
				throw new JNestedTransactionNotSupportedException(
						"Cannot create a nested transaction because savepoints are not supported by your JDBC driver");
			}
			return conHolder.createSavepoint();
		}
		catch (SQLException ex) {
			throw new JCannotCreateTransactionException("Could not create JDBC savepoint", ex);
		}
	}

	/**
	 * This implementation rolls back to the given JDBC 3.0 Savepoint.
	 * @see java.sql.Connection#rollback(java.sql.Savepoint)
	 */
	@Override
	public void rollbackToSavepoint(Object savepoint) throws JTransactionException {
		JConnectionHolder conHolder = getConnectionHolderForSavepoint();
		try {
			conHolder.getConnection().rollback((Savepoint) savepoint);
		}
		catch (Throwable ex) {
			throw new JTransactionSystemException("Could not roll back to JDBC savepoint", ex);
		}
	}

	/**
	 * This implementation releases the given JDBC 3.0 Savepoint.
	 * @see java.sql.Connection#releaseSavepoint
	 */
	@Override
	public void releaseSavepoint(Object savepoint) throws JTransactionException {
		JConnectionHolder conHolder = getConnectionHolderForSavepoint();
		try {
			conHolder.getConnection().releaseSavepoint((Savepoint) savepoint);
		}
		catch (Throwable ex) {
			logger.debug("Could not explicitly release JDBC savepoint", ex);
		}
	}

	protected JConnectionHolder getConnectionHolderForSavepoint() throws JTransactionException {
		if (!isSavepointAllowed()) {
			throw new JNestedTransactionNotSupportedException(
					"Transaction manager does not allow nested transactions");
		}
		if (!hasConnectionHolder()) {
			throw new JTransactionUsageException(
					"Cannot create nested transaction if not exposing a JDBC transaction");
		}
		return getConnectionHolder();
	}

}
