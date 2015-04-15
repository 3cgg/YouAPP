/**
 * 
 */
package j.jave.framework.components.resource.sql;

import j.jave.framework.components.core.autoloader.AbstractSQLDDLLoader;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class ViewsSQLLoader extends AbstractSQLDDLLoader{

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(ViewsSQLLoader.class.getResource("views.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
