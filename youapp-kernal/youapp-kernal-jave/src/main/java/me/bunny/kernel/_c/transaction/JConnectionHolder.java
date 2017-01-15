package me.bunny.kernel._c.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import me.bunny.kernel._c.utils.JAssert;

/**
 * Connection holder, wrapping a JDBC Connection.
 * {@link DataSourceTransactionManager} binds instances of this class
 * to the thread, for a specific DataSource.
 *
 * <p>Inherits rollback-only support for nested JDBC transactions
 * and reference count functionality from the base class.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Juergen Hoeller
 * @since 06.05.2003
 * @see DataSourceTransactionManager
 * @see DataSourceUtils
 */
public class JConnectionHolder  extends JResourceHolderSupport{

	public static final String SAVEPOINT_NAME_PREFIX = "SAVEPOINT_";


	private JConnectionHandle connectionHandle;

	private Connection currentConnection;

	private boolean transactionActive = false;

	private Boolean savepointsSupported;

	private int savepointCounter = 0;


	/**
	 * Create a new ConnectionHolder for the given ConnectionHandle.
	 * @param connectionHandle the ConnectionHandle to hold
	 */
	public JConnectionHolder(JConnectionHandle connectionHandle) {
		JAssert.notNull(connectionHandle, "ConnectionHandle must not be null");
		this.connectionHandle = connectionHandle;
	}

	/**
	 * Create a new ConnectionHolder for the given JDBC Connection,
	 * wrapping it with a {@link SimpleConnectionHandle},
	 * assuming that there is no ongoing transaction.
	 * @param connection the JDBC Connection to hold
	 * @see SimpleConnectionHandle
	 * @see #ConnectionHolder(java.sql.Connection, boolean)
	 */
	public JConnectionHolder(Connection connection) {
		this.connectionHandle = new JSimpleConnectionHandle(connection);
	}

	/**
	 * Create a new ConnectionHolder for the given JDBC Connection,
	 * wrapping it with a {@link SimpleConnectionHandle}.
	 * @param connection the JDBC Connection to hold
	 * @param transactionActive whether the given Connection is involved
	 * in an ongoing transaction
	 * @see SimpleConnectionHandle
	 */
	public JConnectionHolder(Connection connection, boolean transactionActive) {
		this(connection);
		this.transactionActive = transactionActive;
	}


	/**
	 * Return the ConnectionHandle held by this ConnectionHolder.
	 */
	public JConnectionHandle getConnectionHandle() {
		return this.connectionHandle;
	}

	/**
	 * Return whether this holder currently has a Connection.
	 */
	protected boolean hasConnection() {
		return (this.connectionHandle != null);
	}

	/**
	 * Set whether this holder represents an active, JDBC-managed transaction.
	 * @see DataSourceTransactionManager
	 */
	protected void setTransactionActive(boolean transactionActive) {
		this.transactionActive = transactionActive;
	}

	/**
	 * Return whether this holder represents an active, JDBC-managed transaction.
	 */
	protected boolean isTransactionActive() {
		return this.transactionActive;
	}


	/**
	 * Override the existing Connection handle with the given Connection.
	 * Reset the handle if given {@code null}.
	 * <p>Used for releasing the Connection on suspend (with a {@code null}
	 * argument) and setting a fresh Connection on resume.
	 */
	protected void setConnection(Connection connection) {
		if (this.currentConnection != null) {
			this.connectionHandle.releaseConnection(this.currentConnection);
			this.currentConnection = null;
		}
		if (connection != null) {
			this.connectionHandle = new JSimpleConnectionHandle(connection);
		}
		else {
			this.connectionHandle = null;
		}
	}

	/**
	 * Return the current Connection held by this ConnectionHolder.
	 * <p>This will be the same Connection until {@code released}
	 * gets called on the ConnectionHolder, which will reset the
	 * held Connection, fetching a new Connection on demand.
	 * @see ConnectionHandle#getConnection()
	 * @see #released()
	 */
	public Connection getConnection() {
		JAssert.notNull(this.connectionHandle, "Active Connection is required");
		if (this.currentConnection == null) {
			this.currentConnection = this.connectionHandle.getConnection();
		}
		return this.currentConnection;
	}

	/**
	 * Return whether JDBC 3.0 Savepoints are supported.
	 * Caches the flag for the lifetime of this ConnectionHolder.
	 * @throws SQLException if thrown by the JDBC driver
	 */
	public boolean supportsSavepoints() throws SQLException {
		if (this.savepointsSupported == null) {
			this.savepointsSupported = new Boolean(getConnection().getMetaData().supportsSavepoints());
		}
		return this.savepointsSupported.booleanValue();
	}

	/**
	 * Create a new JDBC 3.0 Savepoint for the current Connection,
	 * using generated savepoint names that are unique for the Connection.
	 * @return the new Savepoint
	 * @throws SQLException if thrown by the JDBC driver
	 */
	public Savepoint createSavepoint() throws SQLException {
		this.savepointCounter++;
		return getConnection().setSavepoint(SAVEPOINT_NAME_PREFIX + this.savepointCounter);
	}

	/**
	 * Releases the current Connection held by this ConnectionHolder.
	 * <p>This is necessary for ConnectionHandles that expect "Connection borrowing",
	 * where each returned Connection is only temporarily leased and needs to be
	 * returned once the data operation is done, to make the Connection available
	 * for other operations within the same transaction. This is the case with
	 * JDO 2.0 DataStoreConnections, for example.
	 * @see org.springframework.orm.jdo.DefaultJdoDialect#getJdbcConnection
	 */
	@Override
	public void released() {
		super.released();
		if (!isOpen() && this.currentConnection != null) {
			this.connectionHandle.releaseConnection(this.currentConnection);
			this.currentConnection = null;
		}
	}


	@Override
	public void clear() {
		super.clear();
		this.transactionActive = false;
		this.savepointsSupported = null;
		this.savepointCounter = 0;
	}

	
}
