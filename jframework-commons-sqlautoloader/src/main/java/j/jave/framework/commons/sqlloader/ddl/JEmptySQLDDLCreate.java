package j.jave.framework.commons.sqlloader.ddl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Empty SQL DDL Create Implementation. nothing is done if this instance used. 
 * @author J
 */
public class JEmptySQLDDLCreate extends JAbstractSQLDDLCreate {

	public JEmptySQLDDLCreate(String className, String url, String user,
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
