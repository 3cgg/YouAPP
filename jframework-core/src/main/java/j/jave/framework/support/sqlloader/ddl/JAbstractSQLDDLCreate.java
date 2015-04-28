package j.jave.framework.support.sqlloader.ddl;

import j.jave.framework.support.sqlloader.JAbstractSQLCreate;

/**
 * basic class to create DDL SQL. 
 * extract common method. 
 * @author J
 *
 */
public abstract class JAbstractSQLDDLCreate extends JAbstractSQLCreate implements JSQLDDLCreate {
	
	public JAbstractSQLDDLCreate(String className,String url, String user, String password) {
		super(className, url, user, password);
	}
	
	@Override
	protected boolean validateOnSQL(String sql) {
		boolean done=false;
		if(tables!=null){
			String sqlName=sql.substring(0,sql.indexOf("("));
			String[] ddls=sqlName.split(" ");
			String tableName=ddls[ddls.length-1].trim();
			if(tables.contains(tableName)){
				done=true;
			}
		}
		return !done;
	}
	
}
