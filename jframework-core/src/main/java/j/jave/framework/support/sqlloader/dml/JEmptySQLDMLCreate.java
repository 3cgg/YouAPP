package j.jave.framework.support.sqlloader.dml;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Empty SQL DML Create Implementation. nothing is done if this instance used. 
 * @author J
 */
public class JEmptySQLDMLCreate extends JAbstractSQLDMLCreate {

	public JEmptySQLDMLCreate(String className, String url, String user,
			String password) {
		super(className, url, user, password);
	}

	@Override
	protected List<String> existTables(Connection connection) {
		return new ArrayList<String>();
	}
	
	@Override
	public void create() {
		LOGGER.info(" not do anything "+this.getClass());
		return ;
	}

}
