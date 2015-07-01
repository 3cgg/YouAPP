package j.jave.framework.commons.sqlloader.dml;

import j.jave.framework.commons.sqlloader.JAbstractSQLCreate;

import java.util.Set;

/**
 * basic class to create DDL SQL. 
 * extract common method. 
 * @author J
 *
 */
public abstract class JAbstractSQLDMLCreate extends JAbstractSQLCreate implements JSQLDMLCreate {
	
	public JAbstractSQLDMLCreate(String className,String url, String user, String password) {
		super(className, url, user, password);
	}
	
	@Override
	protected boolean validateOnSQL(String sql) {
		boolean existsTable=false;
		if(this.tables!=null){
			String sqlName=sql.substring(0,sql.indexOf("("));
			String[] ddls=sqlName.split(" ");
			String tableName=ddls[ddls.length-1].trim();
			
			if(!tableRecords.containsKey(tableName) ){
				return false;
			}
			else{
				int count=tableRecords.get(tableName);
				if(count>0){
					// break ; return false to prevent insert record to affect 
					return false;
				}
				else{
					return true;
				}
			}
		}
		return existsTable;
	}

	@Override
	protected Set<Class<?>> getJSQLLoaderClasses(Class<?> clazz) {
		return super.getJSQLLoaderClasses(JSQLDMLLoader.class);
	}
}
