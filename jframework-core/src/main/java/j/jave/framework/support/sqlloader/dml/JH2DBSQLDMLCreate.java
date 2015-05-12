package j.jave.framework.support.sqlloader.dml;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * H2 SQL DML Create Implementation.
 * @author J
 */
public class JH2DBSQLDMLCreate extends JAbstractSQLDMLCreate {

	public JH2DBSQLDMLCreate(String className,String url, String user, String password) {
		super(className, url, user, password);
	}
	
	@Override
	protected List<String> existTables(Connection connection) {
		List<String> tables=new ArrayList<String>();
		Statement stat = null;
		try{
			stat = connection.createStatement();  
	        ResultSet resultSet=stat.executeQuery(" select * from  INFORMATION_SCHEMA.TABLES where TABLE_TYPE='TABLE' AND TABLE_SCHEMA='PUBLIC' ");
	        while (resultSet.next()) {
				String tableName=resultSet.getString("TABLE_NAME");
				tables.add(tableName);
			}
		}catch(Exception e){
			LOGGER.error(" load existing table ", e);
		}finally{
			try {
        		if(stat!=null){
        			stat.close();
        		}
			} catch (SQLException e) {
				LOGGER.error("close statement ", e);
			}  
		}
		return tables;
	}

	

}
