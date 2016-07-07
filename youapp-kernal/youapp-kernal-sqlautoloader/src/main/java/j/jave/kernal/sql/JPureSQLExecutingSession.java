package j.jave.kernal.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JPureSQLExecutingSession implements JSQLExecutingSession {

	private Connection connection;
	
	@Override
	public void execute(String sql, Map<String, Object> params) throws SQLException {
	
	}

	@Override
	public void execute(String sql) throws SQLException{
		connection.createStatement().execute(sql);
	}

	@Override
	public boolean cached() {
		return true;
	}

	@Override
	public void commit() throws Exception {
		connection.commit();
	}

	@Override
	public void rollback() throws Exception {
		connection.rollback();
	}

	@Override
	public JSQLExecutingSession newSession(Connection connection) {
		this.connection=connection;
		return this;
	}
	
	

}
