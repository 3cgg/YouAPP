package me.bunny.kernel.jave.transaction;

import java.sql.Connection;

/**
 * Simple interface to be implemented by handles for a JDBC Connection.
 * Used by JpaDialect and JdoDialect, for example.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see SimpleConnectionHandle
 * @see ConnectionHolder
 * @see org.springframework.orm.jdo.JpaDialect#getJdbcConnection
 * @see org.springframework.orm.jdo.JdoDialect#getJdbcConnection
 */
public interface JConnectionHandle {

	/**
	 * Fetch the JDBC Connection that this handle refers to.
	 */
	Connection getConnection();

	/**
	 * Release the JDBC Connection that this handle refers to.
	 * @param con the JDBC Connection to release
	 */
	void releaseConnection(Connection con);

}
