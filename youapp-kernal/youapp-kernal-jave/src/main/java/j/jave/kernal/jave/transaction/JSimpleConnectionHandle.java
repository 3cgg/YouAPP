package j.jave.kernal.jave.transaction;

import j.jave.kernal.jave.utils.JAssert;

import java.sql.Connection;

/**
 * Simple implementation of the {@link ConnectionHandle} interface,
 * containing a given JDBC Connection.
 *
 * @author Juergen Hoeller
 * @since 1.1
 */
public class JSimpleConnectionHandle implements JConnectionHandle {

	private final Connection connection;


	/**
	 * Create a new SimpleConnectionHandle for the given Connection.
	 * @param connection the JDBC Connection
	 */
	public JSimpleConnectionHandle(Connection connection) {
		JAssert.notNull(connection, "Connection must not be null");
		this.connection = connection;
	}

	/**
	 * Return the specified Connection as-is.
	 */
	@Override
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * This implementation is empty, as we're using a standard
	 * Connection handle that does not have to be released.
	 */
	@Override
	public void releaseConnection(Connection con) {
	}


	@Override
	public String toString() {
		return "SimpleConnectionHandle: " + this.connection;
	}

}
