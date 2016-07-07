package j.jave.kernal.sql;

import j.jave.kernal.jave.proxy.JAtomicResourceSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface JSQLExecutingSession extends JAtomicResourceSession{
	
	public void execute(String sql,Map<String, Object> params) throws SQLException;
	
	public void execute(String sql) throws SQLException;
	
	public JSQLExecutingSession newSession(Connection connection);
}
