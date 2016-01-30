/**
 * 
 */
package j.jave.framework.components.resource.sql;

import j.jave.framework.components.core.autoloader.AbstractSQLDMLLoader;

import java.util.List;

/**
 * @author J
 */
public class ResourceSQLDMLLoader extends AbstractSQLDMLLoader{

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(ResourceSQLDMLLoader.class.getResource("resource-initialized.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
