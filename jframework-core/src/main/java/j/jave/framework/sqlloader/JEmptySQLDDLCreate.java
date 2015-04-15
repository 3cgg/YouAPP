package j.jave.framework.sqlloader;

import java.sql.Connection;
import java.util.List;

public class JEmptySQLDDLCreate extends JAbstractSQLDDLCreate {

	public JEmptySQLDDLCreate(String className, String url, String user,
			String password) {
		super(className, url, user, password);
	}

	@Override
	protected List<String> existTables(Connection connection) {
		return null;
	}
	
	@Override
	public void create() {
		LOGGER.info(" not do anything "+this.getClass());
		return ;
	}

}
