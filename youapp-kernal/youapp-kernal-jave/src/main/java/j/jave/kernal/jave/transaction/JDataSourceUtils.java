package j.jave.kernal.jave.transaction;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JAssert;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JDataSourceUtils {

	private static final JLogger logger = JLoggerFactory.getLogger(JDataSourceUtils.class);

	
	/**
	 * Close the given Connection, obtained from the given DataSource,
	 * if it is not managed externally (that is, not bound to the thread).
	 * @param con the Connection to close if necessary
	 * (if this is {@code null}, the call will be ignored)
	 * @param dataSource the DataSource that the Connection was obtained from
	 * (may be {@code null})
	 * @see #getConnection
	 */
	public static void releaseConnection(Connection con, DataSource dataSource) {
		try {
			doReleaseConnection(con, dataSource);
		}
		catch (SQLException ex) {
			logger.debug("Could not close JDBC Connection", ex);
		}
		catch (Throwable ex) {
			logger.debug("Unexpected exception on closing JDBC Connection", ex);
		}
	}

	/**
	 * Actually close the given Connection, obtained from the given DataSource.
	 * Same as {@link #releaseConnection}, but throwing the original SQLException.
	 * <p>Directly accessed by {@link TransactionAwareDataSourceProxy}.
	 * @param con the Connection to close if necessary
	 * (if this is {@code null}, the call will be ignored)
	 * @param dataSource the DataSource that the Connection was obtained from
	 * (may be {@code null})
	 * @throws SQLException if thrown by JDBC methods
	 * @see #doGetConnection
	 */
	public static void doReleaseConnection(Connection con, DataSource dataSource) throws SQLException {
		if (con == null) {
			return;
		}
		if (dataSource != null) {
			JConnectionHolder conHolder = (JConnectionHolder) JTransactionSynchronizationManager.getResource(dataSource);
			if (conHolder != null && connectionEquals(conHolder, con)) {
				// It's the transactional Connection: Don't close it.
				conHolder.released();
				return;
			}
		}
		logger.debug("Returning JDBC Connection to DataSource");
		doCloseConnection(con, dataSource);
	}

	/**
	 * Close the Connection, unless a {@link SmartDataSource} doesn't want us to.
	 * @param con the Connection to close if necessary
	 * @param dataSource the DataSource that the Connection was obtained from
	 * @throws SQLException if thrown by JDBC methods
	 * @see Connection#close()
	 * @see SmartDataSource#shouldClose(Connection)
	 */
	public static void doCloseConnection(Connection con, DataSource dataSource) throws SQLException {
		if (!(dataSource instanceof JSmartDataSource) || ((JSmartDataSource) dataSource).shouldClose(con)) {
			con.close();
		}
	}
	
	/**
	 * Determine whether the given two Connections are equal, asking the target
	 * Connection in case of a proxy. Used to detect equality even if the
	 * user passed in a raw target Connection while the held one is a proxy.
	 * @param conHolder the ConnectionHolder for the held Connection (potentially a proxy)
	 * @param passedInCon the Connection passed-in by the user
	 * (potentially a target Connection without proxy)
	 * @return whether the given Connections are equal
	 * @see #getTargetConnection
	 */
	private static boolean connectionEquals(JConnectionHolder conHolder, Connection passedInCon) {
		if (!conHolder.hasConnection()) {
			return false;
		}
		Connection heldCon = conHolder.getConnection();
		// Explicitly check for identity too: for Connection handles that do not implement
		// "equals" properly, such as the ones Commons DBCP exposes).
		return (heldCon == passedInCon || heldCon.equals(passedInCon) ||
				getTargetConnection(heldCon).equals(passedInCon));
	}

	/**
	 * Return the innermost target Connection of the given Connection. If the given
	 * Connection is a proxy, it will be unwrapped until a non-proxy Connection is
	 * found. Otherwise, the passed-in Connection will be returned as-is.
	 * @param con the Connection proxy to unwrap
	 * @return the innermost target Connection, or the passed-in one if no proxy
	 * @see ConnectionProxy#getTargetConnection()
	 */
	public static Connection getTargetConnection(Connection con) {
		Connection conToUse = con;
//		while (conToUse instanceof ConnectionProxy) {
//			conToUse = ((ConnectionProxy) conToUse).getTargetConnection();
//		}
		return conToUse;
	}
	
	/**
	 * Prepare the given Connection with the given transaction semantics.
	 * @param con the Connection to prepare
	 * @param definition the transaction definition to apply
	 * @return the previous isolation level, if any
	 * @throws SQLException if thrown by JDBC methods
	 * @see #resetConnectionAfterTransaction
	 */
	public static Integer prepareConnectionForTransaction(Connection con, JTransactionDefinition definition)
			throws SQLException {

		JAssert.notNull(con, "No Connection specified");

		// Set read-only flag.
		if (definition != null && definition.isReadOnly()) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("Setting JDBC Connection [" + con + "] read-only");
				}
				con.setReadOnly(true);
			}
			catch (SQLException ex) {
				Throwable exToCheck = ex;
				while (exToCheck != null) {
					if (exToCheck.getClass().getSimpleName().contains("Timeout")) {
						// Assume it's a connection timeout that would otherwise get lost: e.g. from JDBC 4.0
						throw ex;
					}
					exToCheck = exToCheck.getCause();
				}
				// "read-only not supported" SQLException -> ignore, it's just a hint anyway
				logger.debug("Could not set JDBC Connection read-only", ex);
			}
			catch (RuntimeException ex) {
				Throwable exToCheck = ex;
				while (exToCheck != null) {
					if (exToCheck.getClass().getSimpleName().contains("Timeout")) {
						// Assume it's a connection timeout that would otherwise get lost: e.g. from Hibernate
						throw ex;
					}
					exToCheck = exToCheck.getCause();
				}
				// "read-only not supported" UnsupportedOperationException -> ignore, it's just a hint anyway
				logger.debug("Could not set JDBC Connection read-only", ex);
			}
		}

		// Apply specific isolation level, if any.
		Integer previousIsolationLevel = null;
		if (definition != null && definition.getIsolationLevel() != JTransactionDefinition.ISOLATION_DEFAULT) {
			if (logger.isDebugEnabled()) {
				logger.debug("Changing isolation level of JDBC Connection [" + con + "] to " +
						definition.getIsolationLevel());
			}
			int currentIsolation = con.getTransactionIsolation();
			if (currentIsolation != definition.getIsolationLevel()) {
				previousIsolationLevel = currentIsolation;
				con.setTransactionIsolation(definition.getIsolationLevel());
			}
		}

		return previousIsolationLevel;
	}
	
}
